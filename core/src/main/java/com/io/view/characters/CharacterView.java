package com.io.view.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

public abstract class CharacterView {
    protected final Sprite characterSprite;
    protected final TextureManager tm;
    protected final float size;

    public CharacterView(TextureManager tm, Vector2 position, float size, Texture texture) {
        this.tm = tm;
        this.size = size;

        characterSprite = new Sprite(texture);
        characterSprite.setPosition(position.x, position.y);
        characterSprite.setSize(size, size);
    }

    public abstract void setPosition(Vector2 newPosition);

    public abstract void draw(SpriteBatch batch);
}
