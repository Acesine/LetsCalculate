package com.xianggao.letscalculate.server;

/**
 * Created by Acesine on 1/4/16.
 */
public class HandlationException extends RuntimeException {
    private ErrorCode errorCode;

    public HandlationException(ErrorCode e) {
        this.errorCode = e;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
