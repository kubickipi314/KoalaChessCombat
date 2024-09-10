package com.io.view.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

public class PlayerView {
    private final Sprite sprite;
    private final TextureManager tm;

    public PlayerView(TextureManager tm, Vector2 position, float size) {
        this.tm = tm;
        sprite = new Sprite(tm.getPlayer(0));
        sprite.setPosition(position.x, position.y);
        sprite.setSize(size, size);
    }

    public void setPosition(Vector2 newPosition) {
        sprite.setPosition(newPosition.x, newPosition.y);
    }

    public void setTexture(int stateNumber) {
        sprite.setTexture(tm.getPlayer(stateNumber));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
