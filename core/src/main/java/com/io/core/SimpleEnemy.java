package com.io.core;

import com.io.service.TurnService;

public class SimpleEnemy extends Enemy {
    public SimpleEnemy(TurnService ts) {
        super(ts);
    }

    public Move getNextMove() {
        Board board = ts.getBoard();
        return new Move(new BoardPosition(0, 0), new MoveType(0, MoveEffect.NONE), this);
    }
}
