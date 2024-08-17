package com.io.core;

import com.io.service.TurnService;

public class SimpleEnemy extends Enemy {
    static int maxMana = 10, maxHealth = 5;

    public SimpleEnemy(TurnService ts, BoardPosition position) {
        super(ts, maxMana, maxHealth, position);
    }

    public Move getNextMove(Board board) {
        return null;
    }
}
