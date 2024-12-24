package com.imzhy.fence.exception.authorization;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 拒绝访问
 *
 * @author zhy
 * @since 2024.11.30
 */
public class AccessDeniedException extends AuthorizationException {

    public AccessDeniedException() {
        super(Code.accessDenied.httpStatus, Code.accessDenied.code, Code.accessDenied.message);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    public AccessDeniedException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
