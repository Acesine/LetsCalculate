package com.xianggao.letscalculate.commons;

import java.io.Serializable;

/**
 * Created by Acesine on 1/5/16.
 */
public class GameResponse implements Serializable {
    private static final long serialVersionUID = 7483572295622456147L;

    private String message;

    public GameResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
