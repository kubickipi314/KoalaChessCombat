package com.io.core;

import com.io.service.TurnService;

public abstract class Enemy extends Character {
    public Enemy(TurnService ts, int maxMana, int maxHealth, BoardPosition position) {
        super(ts, maxMana, maxHealth, position);
    }

    @Override
    public void startTurn() {
        while (playMove()) ;

        ts.endTurn();
    }

    abstract Move getNextMove(Board board);

    public boolean playMove() {
        Board board = ts.getBoard();
        Move move = getNextMove(board);

        // if enemy wants to end turn
        if (move == null) return false;

        if (!ts.tryMakeMove(move)) {
            System.out.println("Enemy failed to make a valid move.");
            return false;
        }

        return true;
    }
}
