package com.io.core;

import com.io.service.TurnService;

public class Player extends Character {
    public Player(TurnService ts) {
        super(ts);
    }

    public boolean PlayMove(BoardPosition position, MoveType moveType) {
        Move move = new Move(position, moveType, this);
        return ts.tryMakeMove(move);
    }
}
