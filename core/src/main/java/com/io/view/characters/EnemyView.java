package com.io.view.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

public class EnemyView extends CharacterView {
    private final Sprite healthSprite;

    private final Sprite attackSprite;
    private final float healthOffset;

    public EnemyView(TextureManager tm, Vector2 position, float size) {
        super(tm, position, size, tm.getEnemy());

        healthOffset = size * 0.25f;
        healthSprite = new Sprite(tm.getEnemyHealth());
        healthSprite.setPosition(position.x + healthOffset, position.y);
        healthSprite.setSize(size, size);

        attackSprite = new Sprite(tm.getShuriken(0));
        attackSprite.setPosition(position.x, position.y);
        attackSprite.setSize(size, size);
    }


    public void changeHealth(float percentage) {
        healthSprite.setSize(size * percentage, size);
    }

    @Override
    public void setPosition(Vector2 newPosition) {
        characterSprite.setPosition(newPosition.x, newPosition.y);
        healthSprite.setPosition(newPosition.x + healthOffset, newPosition.y);
    }

    public void setAttackPosition(Vector2 newPosition) {
        attackSprite.setPosition(newPosition.x, newPosition.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        characterSprite.draw(batch);
        healthSprite.draw(batch);
    }

    public void drawAttack(SpriteBatch batch) {
        attackSprite.draw(batch);
    }

    public void setAttackTexture(int attackNumber){
        attackSprite.setTexture(tm.getShuriken(attackNumber));
    }

    public void setTexture(int stateNumber) {
        characterSprite.setTexture(tm.getElephant(stateNumber));
    }
}
