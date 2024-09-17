package com.io.presenter.character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.io.core.board.BoardPosition;

public interface CharacterPresenter {
    void update(BoardPosition position);

    void updatePosition();

    void render(SpriteBatch batch);

    void startMoveAnimation(BoardPosition targetBoardPosition);

    boolean isMoving();

    void attack(BoardPosition position);
}
