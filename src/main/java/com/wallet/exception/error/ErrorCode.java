package com.wallet.exception.error;

public interface ErrorCode {
    /**
     * send back error code for handling proper solution in front end
     * @return error Code 200, 401, 404,...
     */
    String getCode();

    /**
     * send back error or exception description
     * @return error message is a description of relevant error Code
     */
    String getMessage();
}

