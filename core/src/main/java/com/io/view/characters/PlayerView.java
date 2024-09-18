package com.io.view.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

import static com.io.core.CharacterType.PLAYER;

public class PlayerView extends CharacterView {
    public PlayerView(TextureManager tm, Vector2 position, float size) {
        super(tm, position, size, PLAYER);
    }

    @Override
    public void setTexture(int stateNumber) {
        characterSprite.setTexture(tm.getCharacter(PLAYER, stateNumber));
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
