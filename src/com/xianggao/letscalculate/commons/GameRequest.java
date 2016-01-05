package com.xianggao.letscalculate.commons;

import java.io.Serializable;

/**
 * Created by Acesine on 1/4/16.
 */
public class GameRequest implements Serializable{
    private static final long serialVersionUID = 7483572295622456147L;

    private final String userName;
    private final String formula;

    public GameRequest(final String userName, final String formula) {
        this.userName = userName;
        this.formula = formula;
    }

    public String getFormula() {
        return formula;
    }

    public String getUserName() {
        return userName;
    }
}
