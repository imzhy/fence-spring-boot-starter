package com.imzhy.fence.exception.authorization;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 未登录
 *
 * @author zhy
 * @since 2024.12.18
 */
public class UnAuthenticationException extends AuthorizationException {

    public UnAuthenticationException() {
        super(Code.unAuthentication.httpStatus, Code.unAuthentication.code, Code.unAuthentication.message);
    }

    public UnAuthenticationException(Throwable cause) {
        super(cause);
    }

    public UnAuthenticationException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
