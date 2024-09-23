package com.io.presenter.game.character;

import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.managers.game.CoordinatesManager;
import com.io.managers.game.SoundManager;
import com.io.managers.game.TextureManager;
import com.io.view.game.characters.CharacterViewType;
import com.io.view.game.characters.EnemyView;

public class EnemyPresenter extends CharacterPresenter {
    private final int maxHealth;
    private int health;

    public EnemyPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition boardPosition, int maxHealth, CharacterViewType type) {
        super(sm, cm, boardPosition, type);

        Vector2 position = cm.calculatePosition(boardPosition);
        characterView = new EnemyView(tm, position, cm.getTileSize(), type);

        this.maxHealth = maxHealth;
        health = maxHealth;

        ((EnemyView) characterView).changeHealth(1);
    }

    public void setHealth(int value) {
        health = value;
        ((EnemyView) characterView).changeHealth((float) health / maxHealth);
    }
}
