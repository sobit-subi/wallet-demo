package com.wallet.common;

import com.wallet.exception.error.ErrorEnum;
import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;

    private Result (String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(ErrorEnum errorEnum, T data) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
        this.data = data;
    }

    public static <T> Result<T> Result(ErrorEnum errorEnum, T data) {
        return new Result<>(errorEnum, data);
    }

    public static <T> Result<T> Result(String code, String message, T data) {
        return new Result<>(code, message, data);
    }
}
