package com.xianggao.letscalculate.server;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Acesine on 1/4/16.
 */
public class EvaluatorTest extends TestCase {
    @Test
    public void testEval() {
        String input = "4*(2+3)";
        assertEquals(20, Evaluator.eval(input));
    }
}