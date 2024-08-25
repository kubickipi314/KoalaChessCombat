package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.core.moves.MoveDTO;
import com.io.service.TurnService;

import java.util.List;

public class SimpleEnemy extends Enemy {
    static int maxMana = 10, maxHealth = 5;

    public SimpleEnemy(TurnService ts, BoardPosition position, List<Move> moves) {
        super(ts, maxMana, maxHealth, position, moves);
    }

    public MoveDTO getNextMove(Board board) {
        return null;
    }
}
