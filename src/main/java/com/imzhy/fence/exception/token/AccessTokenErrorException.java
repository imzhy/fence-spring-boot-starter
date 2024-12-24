package com.imzhy.fence.exception.token;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * token 错误
 *
 * @author zhy
 * @since 2024.11.30
 */
public class AccessTokenErrorException extends TokenException {

    public AccessTokenErrorException() {
        super(Code.accessTokenError.httpStatus, Code.accessTokenError.code, Code.accessTokenError.message);
    }

    public AccessTokenErrorException(Throwable cause) {
        super(cause);
    }

    public AccessTokenErrorException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
