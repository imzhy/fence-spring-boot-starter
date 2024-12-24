package com.imzhy.fence.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 运行时异常
 *
 * @author zhy
 * @since 2024.11.30
 */
public abstract class SecurityRuntimeException extends RuntimeException {

    private HttpStatus httpStatus;
    private Integer code;
    private Object object;

    public SecurityRuntimeException(Throwable cause) {
        super(cause);
    }

    public SecurityRuntimeException(HttpStatus httpStatus, Integer code, String message) {
        super(message);
        setHttpStatus(httpStatus);
        setCode(code);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = Objects.nonNull(httpStatus) ? httpStatus : HttpStatus.OK;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
