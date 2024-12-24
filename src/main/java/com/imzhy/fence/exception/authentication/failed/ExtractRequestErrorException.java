package com.imzhy.fence.exception.authentication.failed;

import com.imzhy.fence.exception.authentication.AuthenticationException;
import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 提取请求头失败
 *
 * @author zhy
 * @since 2024.12.11
 */
public class ExtractRequestErrorException extends AuthenticationException {

    public ExtractRequestErrorException() {
        super(Code.extractRequestError.httpStatus, Code.extractRequestError.code, Code.extractRequestError.message);
    }

    public ExtractRequestErrorException(Throwable cause) {
        super(cause);
    }

    public ExtractRequestErrorException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
