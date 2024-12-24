package com.imzhy.fence.exception.token;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * token 过期
 *
 * @author zhy
 * @since 2024.11.30
 */
public class RefreshTokenExpiredException extends TokenException {

    public RefreshTokenExpiredException() {
        super(Code.refreshTokenExpired.httpStatus, Code.refreshTokenExpired.code, Code.refreshTokenExpired.message);
    }

    public RefreshTokenExpiredException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenExpiredException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
