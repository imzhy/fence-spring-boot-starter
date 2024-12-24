package com.imzhy.fence.exception.token;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * token 过期
 *
 * @author zhy
 * @since 2024.11.30
 */
public class AccessTokenExpiredException extends TokenException {

    public AccessTokenExpiredException() {
        super(Code.accessTokenExpired.httpStatus, Code.accessTokenExpired.code, Code.accessTokenExpired.message);
    }

    public AccessTokenExpiredException(Throwable cause) {
        super(cause);
    }

    public AccessTokenExpiredException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
