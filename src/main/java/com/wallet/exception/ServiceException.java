package com.wallet.exception;


import com.wallet.exception.error.ErrorEnum;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private final String code;

    public ServiceException(String message) {
        super(message);
        this.code = "500";
    }

    public ServiceException(String errorCode, String message) {
        super(message);
        this.code = errorCode;
    }

    public ServiceException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.code = errorCode;
    }

    public ServiceException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
    }
}
