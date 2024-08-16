package com.io.core;

import com.io.service.TurnService;

public abstract class Enemy extends Character {
    public Enemy(TurnService ts) {
        super(ts);
    }

    abstract Move getNextMove();

    public void PlayMove() {
        Move move = getNextMove();
        if (!ts.tryMakeMove(move)) throw new Error("Enemy failed to make a valid move.");
    }
}
