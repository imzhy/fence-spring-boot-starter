package com.imzhy.fence.exception.authentication.account;

import com.imzhy.fence.exception.authentication.AuthenticationException;
import org.springframework.http.HttpStatus;

/**
 * 账户异常
 *
 * @author zhy
 * @since 2024.12.12
 */
public abstract class AccountAbnormalException extends AuthenticationException {

    public AccountAbnormalException(Throwable cause) {
        super(cause);
    }

    public AccountAbnormalException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
