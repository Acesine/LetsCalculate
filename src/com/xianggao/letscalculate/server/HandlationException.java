package com.xianggao.letscalculate.server;

/**
 * Created by Acesine on 1/4/16.
 */
public class HandlationException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public HandlationException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HandlationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
