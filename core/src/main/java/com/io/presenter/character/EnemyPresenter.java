package com.io.presenter.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.io.core.CharacterType;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.EnemyView;

public class EnemyPresenter extends CharacterPresenter {
    private final int maxHealth;
    private int health;

    public EnemyPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition boardPosition, int maxHealth, CharacterType type) {
        super(sm, cm, boardPosition);

        Vector2 position = cm.calculatePosition(boardPosition);
        characterView = new EnemyView(tm, position, cm.getTileSize(), type);

        this.maxHealth = maxHealth;
        health = maxHealth;

        ((EnemyView) characterView).changeHealth(1);
    }

    public void setHealth(int value) {
        if (value < health)
            sm.playRoarSound();
        health = value;
        ((EnemyView) characterView).changeHealth((float) health / maxHealth);
    }

    @Override
    public void updateState() {
        stateTime += Gdx.graphics.getDeltaTime();
        if (state == 0) {
            if (stateTime >= stateInterval) {
                state = 1;
                characterView.setTexture(state);
                stateTime = 0f;
                stateInterval = randomFloat(5);
            }
        } else {
            if (stateTime >= stateInterval) {
                state = 0;
                characterView.setTexture(state);
                stateTime = 0f;
            }
        }
    }
}
