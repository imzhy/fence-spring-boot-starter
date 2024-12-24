package com.imzhy.fence.util;

import org.springframework.http.HttpStatus;

/**
 * code
 *
 * @author zhy
 * @since 2024.12.18
 */
public enum Code {

    extractRequestError(HttpStatus.UNAUTHORIZED, -1, "认证信息提取失败"),
    accountNotFound(HttpStatus.UNAUTHORIZED, -2, "账户找不到"),
    accountLocked(HttpStatus.UNAUTHORIZED, -3, "账户被锁定"),
    accountExpired(HttpStatus.UNAUTHORIZED, -4, "账户己过期"),
    credentialExpired(HttpStatus.UNAUTHORIZED, -5, "凭证已过期"),
    credentialError(HttpStatus.UNAUTHORIZED, -6, "凭证错误"),
    accessTokenError(HttpStatus.UNAUTHORIZED, -7, "access token 异常"),
    accessTokenExpired(HttpStatus.UNAUTHORIZED, -8, "access token 己过期，请重新获取"),

    unAuthentication(HttpStatus.FORBIDDEN, -1, "未登录"),
    accessDenied(HttpStatus.FORBIDDEN, -2, "无权访问"),
    refreshTokenError(HttpStatus.FORBIDDEN, -3, "refresh token 异常"),
    refreshTokenExpired(HttpStatus.FORBIDDEN, -4, "refresh token 己过期，请重新登录");

    public final HttpStatus httpStatus;
    public final int code;
    public final String message;

    Code(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
