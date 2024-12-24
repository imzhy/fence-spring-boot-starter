package com.imzhy.fence.exception.authentication.failed;

import com.imzhy.fence.exception.authentication.AuthenticationException;
import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 凭证已过期
 *
 * @author zhy
 * @since 2024.12.18
 */
public class CredentialExpiredException extends AuthenticationException {

    public CredentialExpiredException() {
        super(Code.credentialExpired.httpStatus, Code.credentialExpired.code, Code.credentialExpired.message);
    }

    public CredentialExpiredException(Throwable cause) {
        super(cause);
    }

    public CredentialExpiredException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
