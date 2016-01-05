package com.xianggao.letscalculate.server;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by Acesine on 1/4/16.
 */
public class Evaluator {
    public static int eval(String formula) {
        ScriptEngineManager em = new ScriptEngineManager();
        ScriptEngine se = em.getEngineByName("javascript");
        int r;
        try {
            r = (Integer)se.eval(formula);
        } catch (ScriptException e) {
            throw new HandlationException(ErrorCode.INVALID_FORMAT);
        }
        return r;
    }
}
