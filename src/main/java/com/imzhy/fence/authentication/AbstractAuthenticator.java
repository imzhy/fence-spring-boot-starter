package com.imzhy.fence.authentication;

import com.imzhy.fence.authentication.handler.AuthenticationFailedHandler;
import com.imzhy.fence.authentication.handler.AuthenticationSuccessHandler;
import com.imzhy.fence.config.SecurityAccount;
import com.imzhy.fence.exception.authentication.AuthenticationException;
import com.imzhy.fence.util.GsonUtils;
import com.imzhy.fence.util.RequestMatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 认证器
 *
 * @author zhy
 * @since 2024.12.4
 */
public abstract class AbstractAuthenticator {

    private final RequestMatcher requestMatcher;
    private final Map<String, AuthenticationFailedHandler> authenticationFailedHandlerMap = new LinkedHashMap<>();
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    public AbstractAuthenticator(String pattern, HttpMethod httpMethod) {
        this.requestMatcher = new RequestMatcher(pattern, httpMethod);
        this.authenticationFailedHandlerMap.put("defaultAuthenticationFailedHandler", (request, response, authenticationException) -> {
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(authenticationException.getHttpStatus().value());
            Map<String, Object> result = new HashMap<>();
            result.put("code", authenticationException.getCode());
            result.put("message", authenticationException.getMessage());
            response.getWriter().write(Objects.requireNonNull(GsonUtils.getToString(result)));
            return false;
        });
        this.authenticationSuccessHandler = ((request, response, token, securityAccount) -> {
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpStatus.OK.value());
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("data", token);
            response.getWriter().write(Objects.requireNonNull(GsonUtils.getToString(result)));
        });
    }

    public Map<String, AuthenticationFailedHandler> getAuthenticationFailedHandlerMap() {
        return this.authenticationFailedHandlerMap;
    }

    public void setAuthenticationFailedHandler(String failedHandlerName, AuthenticationFailedHandler authenticationFailedHandler) {
        if (Objects.nonNull(authenticationFailedHandler)) {
            Map<String, AuthenticationFailedHandler> map = new LinkedHashMap<>();
            map.put(failedHandlerName, authenticationFailedHandler);
            map.putAll(this.authenticationFailedHandlerMap);

            this.authenticationFailedHandlerMap.clear();
            this.authenticationFailedHandlerMap.putAll(map);
        }
    }

    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return this.authenticationSuccessHandler;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        if (Objects.nonNull(authenticationSuccessHandler)) {
            this.authenticationSuccessHandler = authenticationSuccessHandler;
        }
    }

    /**
     * 是否匹配此认证器
     *
     * @param request request
     * @return 是否匹配
     */
    boolean matches(HttpServletRequest request) {
        return this.requestMatcher.matches(request);
    }

    /**
     * 提取请求头信息用于认证
     *
     * @param request  request
     * @param response response
     * @return 从请求头中提取的认证信息
     * @throws AuthenticationException 认证异常
     */
    public abstract AuthenticationRequest extractAuthenticationRequest(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException;

    /**
     * 认证前置处理可以做一些事
     *
     * @param authenticationRequest 从请求头中提取的认证信息
     * @param request               request
     * @param response              response
     * @throws AuthenticationException 认证异常
     */
    public void beforeAuthentication(AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    }

    /**
     * 开始认证流程
     *
     * @param authenticationRequest 从请求头中提取的认证信息
     * @param request               request
     * @param response              response
     * @throws AuthenticationException 包含认证结果
     */
    public abstract void authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException;

    /**
     * 认证后置处理可以做一些事
     *
     * @param securityAccount       securityAccount 认证成功后的用户信息
     * @param authenticationRequest 从请求头中提取的认证信息
     * @param request               request
     * @param response              response
     * @throws AuthenticationException 认证异常
     */
    public void afterAuthentication(SecurityAccount securityAccount, AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    }
}
