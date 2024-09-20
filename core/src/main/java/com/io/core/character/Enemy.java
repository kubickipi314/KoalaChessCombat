package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.MoveDTO;

public abstract class Enemy extends Character {
    public Enemy(int maxMana, int maxHealth, BoardPosition position) {
        super(maxMana, maxHealth, position, 1);
    }

    public abstract MoveDTO makeNextMove(Board board);
}
