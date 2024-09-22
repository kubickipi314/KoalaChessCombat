package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.MoveDTO;
import com.io.db.entity.CharacterEntity;

public abstract class Enemy extends Character {
    public Enemy(int maxMana, int maxHealth, BoardPosition position, Board board) {
        super(maxMana, maxHealth, position, 1, board);
    }

    public Enemy(int maxMana, int maxHealth, CharacterEntity che, Board board) {
        super(maxMana, maxHealth, che, board);
    }

    public abstract MoveDTO makeNextMove();
}
