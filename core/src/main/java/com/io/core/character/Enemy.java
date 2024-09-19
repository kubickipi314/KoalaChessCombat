package com.io.core.character;

import com.io.core.board.BoardPosition;
import com.io.db.entity.CharacterEntity;
import com.io.presenter.GamePresenter;
import com.io.service.GameService;

public abstract class Enemy extends Character {
    public Enemy(GameService gs, GamePresenter gp, int maxMana, int maxHealth, BoardPosition position) {
        super(gs, gp, maxMana, maxHealth, position, 1);
    }

    public Enemy(GameService gs, GamePresenter gp, int maxMana, int maxHealth, CharacterEntity che) {
        super(gs, gp, maxMana, maxHealth, che);
    }

    public abstract boolean makeNextMove();
}
