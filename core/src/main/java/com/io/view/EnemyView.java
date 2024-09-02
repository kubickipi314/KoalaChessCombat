package com.io.view;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnemyView {
    private final Sprite enemySprite;
    private final Sprite healthSprite;
    private final float size;
    private final float positionOffset;

    public EnemyView(TextureManager tm, Vector2 position, float size){
        this.size = size;
        enemySprite = new Sprite(tm.getPlayer());
        enemySprite.setPosition(position.x,position.y);
        enemySprite.setSize(size,size);

        positionOffset = size * 0.25f;
        healthSprite = new Sprite(tm.getEnemyHealth());
        healthSprite.setPosition(position.x + positionOffset, position.y);
        healthSprite.setSize(size, size);
    }
    public void changePosition(Vector2 newPosition){
        enemySprite.setPosition(newPosition.x, newPosition.y);
        healthSprite.setPosition(newPosition.x + positionOffset, newPosition.y);
    }
    public void changeHealth(float percentage){
        healthSprite.setSize(size * percentage, size);
    }

    public void draw(SpriteBatch batch) {
        enemySprite.draw(batch);
        healthSprite.draw(batch);
    }
}
