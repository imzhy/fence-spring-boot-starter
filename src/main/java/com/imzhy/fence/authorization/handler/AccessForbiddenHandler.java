package com.imzhy.fence.authorization.handler;

import com.imzhy.fence.exception.authorization.AccessDeniedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 拒绝访问处理器
 *
 * @author zhy
 * @since 2024.11.30
 */
public interface AccessForbiddenHandler {

    /**
     * 无权访问处理办法
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    void handler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException;
}
