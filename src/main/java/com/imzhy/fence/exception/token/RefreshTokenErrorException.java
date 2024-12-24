package com.imzhy.fence.exception.token;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * token 错误
 *
 * @author zhy
 * @since 2024.11.30
 */
public class RefreshTokenErrorException extends TokenException {

    public RefreshTokenErrorException() {
        super(Code.refreshTokenError.httpStatus, Code.refreshTokenError.code, Code.refreshTokenError.message);
    }

    public RefreshTokenErrorException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenErrorException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
