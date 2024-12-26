package com.imzhy.fence.default_impl.authenticator.passcode;

import com.google.gson.reflect.TypeToken;
import com.imzhy.fence.authentication.AbstractAuthenticator;
import com.imzhy.fence.authentication.AuthenticationRequest;
import com.imzhy.fence.config.SecurityAccount;
import com.imzhy.fence.exception.authentication.AuthenticationException;
import com.imzhy.fence.util.GsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
    public AuthenticationRequest extractAuthenticationRequest(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String contentType = request.getContentType();
        AuthenticationRequest authenticationRequest = null;

        if (Objects.nonNull(contentType)) {
            if (contentType.contains("application/json") || contentType.contains("application/x-www-form-urlencoded")) {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String body = stringBuilder.toString();

                if (StringUtils.isNotBlank(body)) {
                    if (contentType.contains("application/json")) {
                        HashMap<String, String> map = GsonUtils.get(body, new TypeToken<>() {
                        });
                        if (Objects.nonNull(map)) {
                            String username = map.get(KEY_USERNAME);
                            String passcode = map.get(KEY_PASSCODE);
                            username = StringUtils.trimToEmpty(username);
                            passcode = StringUtils.trimToEmpty(passcode);
                            authenticationRequest = new AuthenticationRequest(username, passcode);
                        }
                    } else {
                        String username = "";
                        String passcode = "";
                        for (String param : body.split("&")) {
                            String[] entry = param.split("=");
                            if (entry.length > 1) {
                                String decodedKey = URLDecoder.decode(entry[0], StandardCharsets.UTF_8);
                                String decodedValue = URLDecoder.decode(entry[1], StandardCharsets.UTF_8);
                                decodedValue = StringUtils.trimToEmpty(decodedValue);
                                if (KEY_USERNAME.equalsIgnoreCase(decodedKey)) {
                                    username = decodedValue;
                                } else if (KEY_PASSCODE.equalsIgnoreCase(decodedKey)) {
                                    passcode = decodedValue;
                                }
                            }
                        }
                        authenticationRequest = new AuthenticationRequest(username, passcode);
                    }
                }
            } else if (contentType.contains("multipart/form-data")) {
                String username = "";
                String passcode = "";
                for (String paramName : request.getParameterMap().keySet()) {
                    String[] values = request.getParameterValues(paramName);
                    if (values != null && values.length > 0) {
                        String value = StringUtils.trimToEmpty(values[0]);
                        if (KEY_USERNAME.equalsIgnoreCase(paramName)) {
                            username = value;
                        } else if (KEY_PASSCODE.equalsIgnoreCase(paramName)) {
                            passcode = value;
                        }
                    }
                }
                authenticationRequest = new AuthenticationRequest(username, passcode);
            }
        }

        if (Objects.nonNull(authenticationRequest)) {
            logger.debug("密码登录，已trim()，username: {}, passcode: {}", authenticationRequest.getCredential(), authenticationRequest.getPrincipal());
        }

        return authenticationRequest;
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
