package com.imzhy.fence.authentication.handler;

import com.imzhy.fence.exception.authentication.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证异常处理
 *
 * @author zhy
 * @since 2024.12.10
 */
@FunctionalInterface
public interface AuthenticationFailedHandler {

    /**
     * 处理 handle
     *
     * @param request                 request
     * @param response                response
     * @param authenticationException authenticationException
     * @throws Exception 别的异常
     */
    void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws Exception;
}