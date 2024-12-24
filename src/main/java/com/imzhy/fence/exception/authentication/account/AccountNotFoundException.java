package com.imzhy.fence.exception.authentication.account;

import com.imzhy.fence.util.Code;
import org.springframework.http.HttpStatus;

/**
 * 账户找不到
 *
 * @author zhy
 * @since 2024.12.18
 */
public class AccountNotFoundException extends AccountAbnormalException {

    public AccountNotFoundException() {
        super(Code.accountNotFound.httpStatus, Code.accountNotFound.code, Code.accountNotFound.message);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }

    public AccountNotFoundException(HttpStatus httpStatus, Integer code, String message) {
        super(httpStatus, code, message);
    }
}
