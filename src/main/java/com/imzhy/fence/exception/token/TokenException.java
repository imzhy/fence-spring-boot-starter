package com.imzhy.fence.exception.token;

import com.imzhy.fence.exception.SecurityRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * token 异常
 *
 * @author zhy
 * @since 2024.12.18
 */
public abstract class TokenException extends SecurityRuntimeException {

    public TokenException(Throwable cause) {
        super(cause);
    }

    public TokenException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
