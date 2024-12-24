package com.imzhy.fence.authentication;

import com.imzhy.fence.authentication.handler.AuthenticationFailedHandler;
import com.imzhy.fence.config.SecurityAccount;
import com.imzhy.fence.config.SecurityConfigureAdapter;
import com.imzhy.fence.exception.authentication.AuthenticationException;
import com.imzhy.fence.exception.authentication.account.AccountExpiredException;
import com.imzhy.fence.exception.authentication.account.AccountLockedException;
import com.imzhy.fence.exception.authentication.account.AccountNotFoundException;
import com.imzhy.fence.exception.authentication.failed.CredentialErrorException;
import com.imzhy.fence.exception.authentication.failed.CredentialExpiredException;
import com.imzhy.fence.exception.authentication.failed.ExtractRequestErrorException;
import com.imzhy.fence.token.Token;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Order(1)
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    List<AbstractAuthenticator> authenticatorArr = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        AbstractAuthenticator authenticator = SecurityConfigureAdapter.getAuthenticatorArr()
                .stream()
                .filter(abstractAuthenticator -> abstractAuthenticator.matches(request))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(authenticator)) {
            logger.debug("匹配到认证器：{}", authenticator.getClass().getName());
            try {
                // 提取认证信息
                AuthenticationRequest authenticationRequest = authenticator.extractAuthenticationRequest(request, response);
                if (Objects.isNull(authenticationRequest)) throw new ExtractRequestErrorException();
                // 认证前置处理
                authenticator.beforeAuthentication(authenticationRequest, request, response);
                // 开认证
                authenticator.authenticate(authenticationRequest, request, response);
                SecurityAccount securityAccount = authenticationRequest.getSecurityAccount();
                // 账户是否存在
                if (ObjectUtils.anyNull(securityAccount, securityAccount.getId())) throw new AccountNotFoundException();
                // 是否认证通过
                if (!authenticationRequest.isAuthenticated()) throw new CredentialErrorException();
                // 账户是否被锁定
                if (securityAccount.isAccountLocked()) throw new AccountLockedException();
                long timestamp = System.currentTimeMillis();
                // 账户是否过期
                if (Objects.nonNull(securityAccount.getAccountExpiredTime())
                        && timestamp > securityAccount.getAccountExpiredTime())
                    throw new AccountExpiredException();
                // 凭证是否过期
                if (Objects.nonNull(securityAccount.getCredentialExpiredTime())
                        && timestamp > securityAccount.getCredentialExpiredTime())
                    throw new CredentialExpiredException();
                // 认证后置处理
                authenticator.afterAuthentication(securityAccount, authenticationRequest, request, response);

                // 开始颁发 token
                SecurityConfigureAdapter.TokenConfig tokenConfig = SecurityConfigureAdapter.getTokenConfig();
                Token token = Token.build(tokenConfig);
                // 存储 token
                tokenConfig.getTokenStore().storeToken(token, securityAccount);
                // 认证成功 handler 处理
                authenticator.getAuthenticationSuccessHandler().handle(request, response, token, securityAccount);
            } catch (AuthenticationException authenticationException) {
                if (Objects.nonNull(authenticator.getAuthenticationFailedHandlerMap())) {
                    for (AuthenticationFailedHandler authenticationFailedHandler : authenticator.getAuthenticationFailedHandlerMap().values()) {
                        try {
                            authenticationFailedHandler.handle(request, response, authenticationException);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
