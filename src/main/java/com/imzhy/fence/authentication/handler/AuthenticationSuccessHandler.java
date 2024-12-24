package com.imzhy.fence.authentication.handler;

import com.imzhy.fence.config.SecurityAccount;
import com.imzhy.fence.token.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证成功处理
 *
 * @author zhy
 * @since 2024.12.24
 */
@FunctionalInterface
public interface AuthenticationSuccessHandler {

    /**
     * 处理 handle
     *
     * @param request         request
     * @param response        response
     * @param token           认证成功颁发的 token
     * @param securityAccount 认证通过后的账户信息
     * @throws Exception 别的异常
     */
    void handle(HttpServletRequest request, HttpServletResponse response, Token token, SecurityAccount securityAccount) throws Exception;
}
