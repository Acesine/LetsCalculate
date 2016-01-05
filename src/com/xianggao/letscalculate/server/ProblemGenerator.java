package com.xianggao.letscalculate.server;

import java.util.Random;

/**
 * Created by Acesine on 1/5/16.
 */
public class ProblemGenerator {
    private final int [] cards;

    public ProblemGenerator() {
        cards = new int[13];
    }

    public Problem getProblem() {
        reset();
        String question = "";
        Random rd = new Random();
        int i = 0;
        while (i<4) {
            int choose = rd.nextInt(13);
            if (cards[choose] > 0) {
                question += choose+" ";
                i++;
            }
        }
        return new Problem(question);
    }

    private void reset() {
        for (int i=0;i<13;i++) {
            cards[i] = 4;
        }
    }
}
