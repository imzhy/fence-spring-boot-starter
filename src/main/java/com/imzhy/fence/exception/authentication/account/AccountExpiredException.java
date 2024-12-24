package com.imzhy.fence.exception.authentication.account;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 账户已过期
 *
 * @author zhy
 * @since 2024.12.18
 */
public class AccountExpiredException extends AccountAbnormalException {

    public AccountExpiredException() {
        super(Code.accountExpired.httpStatus, Code.accountExpired.code, Code.accountExpired.message);
    }

    public AccountExpiredException(Throwable cause) {
        super(cause);
    }

    public AccountExpiredException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
