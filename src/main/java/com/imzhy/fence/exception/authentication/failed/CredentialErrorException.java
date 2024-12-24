package com.imzhy.fence.exception.authentication.failed;

import com.imzhy.fence.exception.authentication.AuthenticationException;
import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 凭证错误
 *
 * @author zhy
 * @since 2024.12.18
 */
public class CredentialErrorException extends AuthenticationException {

    public CredentialErrorException() {
        super(Code.credentialError.httpStatus, Code.credentialError.code, Code.credentialError.message);
    }

    public CredentialErrorException(Throwable cause) {
        super(cause);
    }

    public CredentialErrorException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
