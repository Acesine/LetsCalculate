package com.xianggao.letscalculate.commons;

import java.io.Serializable;

/**
 * Created by Acesine on 1/5/16.
 */
public class GameResponse implements Serializable {
    private static final long serialVersionUID = 7483572295622456147L;

    public enum ResponseType {
        BEACON,
        PROBLEM,
        VALIDATE,
        BROADCAST
    }

    private ResponseType type;
    private String message;

    public GameResponse(final ResponseType type, final String message) {
        this.type = type;
        this.message = message;
    }

    public ResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
