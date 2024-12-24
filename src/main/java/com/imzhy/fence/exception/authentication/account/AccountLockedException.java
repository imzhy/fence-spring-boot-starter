package com.imzhy.fence.exception.authentication.account;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 账户被锁定
 *
 * @author zhy
 * @since 2024.12.18
 */
public class AccountLockedException extends AccountAbnormalException {

    public AccountLockedException() {
        super(Code.accountLocked.httpStatus, Code.accountLocked.code, Code.accountLocked.message);
    }

    public AccountLockedException(Throwable cause) {
        super(cause);
    }

    public AccountLockedException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
