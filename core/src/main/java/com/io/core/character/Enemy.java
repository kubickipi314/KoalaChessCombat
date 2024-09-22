package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.MoveDTO;
import com.io.db.entity.CharacterEntity;

public abstract class Enemy extends Character {
    public Enemy(int maxMana, int maxHealth, BoardPosition position) {
        super(maxMana, maxHealth, position, CONST.ENEMY_TEAM);
    }

    public Enemy(int maxMana, int maxHealth, CharacterEntity che) {
        super(maxMana, maxHealth, che);
    }

    public abstract MoveDTO makeNextMove(Board board);
}
