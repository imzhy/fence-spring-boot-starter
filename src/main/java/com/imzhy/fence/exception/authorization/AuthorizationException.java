package com.imzhy.fence.exception.authorization;

import com.imzhy.fence.exception.SecurityRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * 授权失败
 *
 * @author zhy
 * @since 2024.12.18
 */
public abstract class AuthorizationException extends SecurityRuntimeException {

    public AuthorizationException(Throwable cause) {
        super(cause);
    }

    public AuthorizationException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
