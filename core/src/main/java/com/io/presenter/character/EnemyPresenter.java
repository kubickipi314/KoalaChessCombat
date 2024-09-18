package com.io.presenter.character;

import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.EnemyView;

public class EnemyPresenter extends CharacterPresenter {

    private final int maxHealth;
    private int health;


    public EnemyPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition startBoardPosition, int maxHealth) {
        super(tm, sm, cm, startBoardPosition);

        Vector2 position = cm.calculatePosition(boardPosition);
        characterView = new EnemyView(tm, position, cm.getTileSize());

        this.maxHealth = maxHealth;
        health = maxHealth;


        ((EnemyView)characterView).changeHealth(1);
    }

    public void setHealth(int value) {
        if (value < health)
            sm.playRoarSound();
        health = value;
        ((EnemyView)characterView).changeHealth((float) health / maxHealth);
    }
}
