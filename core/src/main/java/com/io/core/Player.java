package com.io.core;

import com.io.service.TurnService;
import com.io.viewmodel.GameViewModel;

public class Player extends Character {
    static int maxMana = 10, maxHealth = 5;

    GameViewModel gvm;

    public Player(TurnService ts, GameViewModel gvm, BoardPosition position) {
        super(ts, maxMana, maxHealth, position);

        this.gvm = gvm;
    }

    @Override
    public void startTurn() {
        gvm.startTurn();
    }

    public boolean PlayMove(BoardPosition position, MoveType moveType) {
        Move move = new Move(position, moveType, this);
        return ts.tryMakeMove(move);
    }
}
