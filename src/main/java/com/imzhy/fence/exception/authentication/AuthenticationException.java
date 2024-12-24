package com.imzhy.fence.exception.authentication;

import com.imzhy.fence.exception.SecurityRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * 认证失败
 *
 * @author zhy
 * @since 2024.12.10
 */
public abstract class AuthenticationException extends SecurityRuntimeException {

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
