package com.wallet.exception.error;

public enum ErrorEnum implements ErrorCode {
    /**
     * success
     */
    SUCCESS("SUCCESS", "success"),
    /**
     * request failed (wrong request url)
     */
    REQUEST_FAILED("REQUEST_FAILED", "request failed"),
    /**
     * Input data is not valid
     */
    REQUEST_PARAMETER_ERROR("REQUEST_PARAMETER_ERROR", "request parameter error"),

    /**
     * system internal (configuration or environment) error or exception
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "system error"),
    /**
     * operation timeout
     */
    OPERATION_TIMEOUT("OPERATION_TIMEOUT", "operation timeout"),
    /**
     * operation conflict, error etc
     */
    OPERATION_FAILED("OPERATION_FAILED", "operation failed"),
    /**
     * database error (wrong SQL execution or non exist parameter)
     */
    DB_ERROR("DB_ERROR", "database error");

    /**
     * result code
     */
    private String code;
    /**
     * result message
     */
    private String message;

    ErrorEnum(final String code, final String description) {
        this.code = code;
        this.message = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

