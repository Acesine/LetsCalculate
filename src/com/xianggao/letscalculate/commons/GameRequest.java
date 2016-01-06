package com.xianggao.letscalculate.commons;

import java.io.Serializable;

/**
 * Created by Acesine on 1/4/16.
 */
public class GameRequest implements Serializable{
    private static final long serialVersionUID = 7483572295622456147L;

    private final String userName;
    private final String msg;

    public GameRequest(final String userName, final String msg) {
        this.userName = userName;
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }

    public String getUserName() {
        return userName;
    }
}
