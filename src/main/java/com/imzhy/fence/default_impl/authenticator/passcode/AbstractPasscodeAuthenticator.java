package com.imzhy.fence.default_impl.authenticator.passcode;

import com.imzhy.fence.authentication.AbstractAuthenticator;
import com.imzhy.fence.authentication.AuthenticationRequest;
import com.imzhy.fence.config.SecurityAccount;
import com.imzhy.fence.exception.authentication.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * 密码认证器
 *
 * @author zhy
 * @since 2024.12.13
 */
public abstract class AbstractPasscodeAuthenticator extends AbstractAuthenticator {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPasscodeAuthenticator.class);

    private static final String DEFAULT_REQUEST_URI = "/login/passcode";
    private static final HttpMethod DEFAULT_REQUEST_METHOD = HttpMethod.POST;

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSCODE = "passcode";

    private PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(32, 64, 1, 1 << 14, 2);

    public AbstractPasscodeAuthenticator() {
        super(DEFAULT_REQUEST_URI, DEFAULT_REQUEST_METHOD);
    }

    public AbstractPasscodeAuthenticator(String uri) {
        super(uri, DEFAULT_REQUEST_METHOD);
    }

    public AbstractPasscodeAuthenticator(String uri, HttpMethod httpMethod) {
        super(uri, httpMethod);
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 加载用户信息
     *
     * @param authenticationRequest 提取到的认证信息
     * @param request               request
     * @return 账户信息
     * @throws AuthenticationException 异常
     */
    public abstract SecurityAccount loadAccount(AuthenticationRequest authenticationRequest, HttpServletRequest request) throws AuthenticationException;

    /**
     * 验证密码
     *
     * @param authenticationRequest 提取到的认证信息
     * @param securityAccount       加载到的用户信息
     * @return 验证是否通过
     * @throws AuthenticationException 异常
     */
    public boolean validatePasscode(AuthenticationRequest authenticationRequest, SecurityAccount securityAccount) throws AuthenticationException {
        return ObjectUtils.allNotNull(authenticationRequest, securityAccount, authenticationRequest.getCredential(), securityAccount.getCredential())
                && passwordEncoder.matches((String) authenticationRequest.getCredential(), (String) securityAccount.getCredential());
    }

    @Override
    public AuthenticationRequest extractAuthenticationRequest(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter(KEY_USERNAME);
        String passcode = request.getParameter(KEY_PASSCODE);
        username = StringUtils.trimToEmpty(username);
        passcode = StringUtils.trimToEmpty(passcode);
        logger.debug("密码登录，已trim()，username: {}, passcode: {}", username, passcode);

        return new AuthenticationRequest(username, passcode);
    }

    @Override
    public void authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        SecurityAccount securityAccount = loadAccount(authenticationRequest, request);
        if (Objects.isNull(securityAccount)) return;
        boolean valid = validatePasscode(authenticationRequest, securityAccount);
        authenticationRequest.setSecurityAccount(securityAccount);
        authenticationRequest.setAuthenticated(valid);
    }
}
