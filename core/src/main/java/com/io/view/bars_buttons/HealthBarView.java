package com.io.view.bars_buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

public class HealthBarView {

    final int MAX_HEART_NUMBER = 10;
    int heartNumber = 8;
    Sprite backgroudSprite;
    Sprite[] heartSprites;

    public HealthBarView(TextureManager tm, Vector2 position, float barHeight) {
        float barLength = barHeight * 7;
        backgroudSprite = new Sprite(tm.getBarBackground());
        backgroudSprite.setPosition(position.x, position.y);
        backgroudSprite.setSize(barLength, barHeight);


        heartSprites = new Sprite[MAX_HEART_NUMBER];

        for (int i = 0; i < MAX_HEART_NUMBER; i++) {
            heartSprites[i] = new Sprite(tm.getHeart());
            float heartX = position.x + barHeight * i * 2 / 3;
            heartSprites[i].setPosition(heartX, position.y);
            heartSprites[i].setSize(barHeight, barHeight);
        }

    }

    public void draw(SpriteBatch batch) {
        backgroudSprite.draw(batch);
        for (int i = 0; i < heartNumber; i++) {
            heartSprites[i].draw(batch);
        }
    }

    public void setHealth(int newHealth) {
        heartNumber = newHealth;
    }
}
