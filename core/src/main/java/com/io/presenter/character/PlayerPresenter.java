package com.io.presenter.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.PlayerView;


public class PlayerPresenter extends CharacterPresenter {

    private float stateTime = 0;
    private int stateNumber = 0;

    public PlayerPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition startBoardPosition, int maxHealth) {
        super(tm, sm, cm, startBoardPosition);

        Vector2 position = cm.calculatePosition(boardPosition);
        characterView = new PlayerView(tm, position, cm.getTileSize());
    }
    @Override
    public void update() {
        updateState();
        if (isMoving) updateMove();
    }

    public void updateState() {
        stateTime += Gdx.graphics.getDeltaTime();
        if (stateNumber == 0) {
            if (stateTime >= 3.0f) {
                stateNumber = 1;
                ((PlayerView) characterView).setTexture(stateNumber);
                stateTime = 0f;
            }
        } else {
            if (stateTime >= 0.3f) {
                stateNumber = 0;
                ((PlayerView) characterView).setTexture(stateNumber);
                stateTime = 0f;
            }
        }
    }
}

