package com.imooc.exception;

public class ApiException extends RuntimeException {

    private Integer code;

    private String msg;

    public ApiException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
