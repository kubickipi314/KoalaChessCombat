package com.io.view.game.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.managers.game.TextureManager;


public class PlayerView extends CharacterView {
    public PlayerView(TextureManager tm, Vector2 position, float size, CharacterViewType type) {
        super(tm, position, size, type);
    }

    @Override
    public void setPosition(Vector2 newPosition) {
        characterSprite.setPosition(newPosition.x, newPosition.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        characterSprite.draw(batch);
    }
}
