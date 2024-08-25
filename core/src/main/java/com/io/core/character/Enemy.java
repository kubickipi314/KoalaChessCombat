package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.core.moves.MoveDTO;
import com.io.service.TurnService;

import java.util.List;

public abstract class Enemy extends Character {
    public Enemy(TurnService ts, int maxMana, int maxHealth, BoardPosition position, List<Move> moves) {
        super(ts, maxMana, maxHealth, position, 1, moves);
    }

    @Override
    public void startTurn() {
        while (playMove()) ;

        ts.endTurn();
    }

    abstract MoveDTO getNextMove(Board board);

    boolean playMove() {
        Board board = ts.getBoard();
        MoveDTO moveDTO = getNextMove(board);

        // if enemy wants to end turn
        if (moveDTO == null) return false;

        if (!ts.tryMakeMove(moveDTO)) {
            System.out.println("Enemy failed to make a valid move.");
            return false;
        }

        return true;
    }
}
