package com.io.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnemyView {
    private final Sprite enemySprite;
    private final Sprite healthSprite;

    public EnemyView(Texture enemyTexture, Texture healthTexture, Vector2 position, int size){
        enemySprite = new Sprite(enemyTexture);
        enemySprite.setPosition(position.x,position.y);
        enemySprite.setSize(size,size);
        healthSprite = new Sprite(enemySprite);
        healthSprite.setTexture(healthTexture);
    }
    public void changePosition(Vector2 newPosition){
        enemySprite.setPosition(newPosition.x, newPosition.y);
    }
    public void changeHealth(Texture healthTexture){
        healthSprite.setTexture(healthTexture);
    }

    public void draw(SpriteBatch batch) {
        enemySprite.draw(batch);
        healthSprite.draw(batch);
    }
}
