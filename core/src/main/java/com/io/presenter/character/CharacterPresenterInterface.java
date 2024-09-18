package com.io.presenter.character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.io.core.board.BoardPosition;

public interface CharacterPresenterInterface {

    boolean isActive();

    void startAttack(BoardPosition position);

    void startMove(BoardPosition position);

    void update();

    void render(SpriteBatch batch);
}
