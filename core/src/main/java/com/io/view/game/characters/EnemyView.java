package com.io.view.game.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.managers.game.TextureManager;

public class EnemyView extends CharacterView {
    private final Sprite healthSprite;
    private final float healthOffset;

    public EnemyView(TextureManager tm, Vector2 position, float size, CharacterViewType type) {
        super(tm, position, size, type);

        healthOffset = size * 0.25f;
        healthSprite = new Sprite(tm.getEnemyHealth());
        healthSprite.setPosition(position.x + healthOffset, position.y);
        healthSprite.setSize(size, size);
    }

    public void changeHealth(float percentage) {
        healthSprite.setSize(size * percentage, size);
    }

    @Override
    public void setPosition(Vector2 newPosition) {
        characterSprite.setPosition(newPosition.x, newPosition.y);
        healthSprite.setPosition(newPosition.x + healthOffset, newPosition.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        characterSprite.draw(batch);
        healthSprite.draw(batch);
    }

}
