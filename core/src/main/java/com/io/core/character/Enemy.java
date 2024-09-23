package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.MoveDTO;
import com.io.db.entity.CharacterEntity;
import com.io.service.EnemyInterface;

public abstract class Enemy extends Character implements EnemyInterface {
    public Enemy(int maxMana, int maxHealth, BoardPosition position, Board board) {
        super(maxMana, maxHealth, position, CONST.ENEMY_TEAM, board);
    }

    public Enemy(int maxMana, int maxHealth, CharacterEntity che, Board board) {
        super(maxMana, maxHealth, che, board);
    }

    public abstract MoveDTO makeNextMove();
}
