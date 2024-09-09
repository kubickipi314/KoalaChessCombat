package com.io.view.bars_buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.CONST;
import com.io.view.assets_managers.TextureManager;

public class ManaBarView {

    final int MAX_MANA_NUMBER = CONST.MAX_PLAYER_MANA;
    int manaNumber;
    Sprite backgroudSprite;
    Sprite[] manaSprites;

    public ManaBarView(TextureManager tm, Vector2 position, float barHeight) {

        float barLength = barHeight * 7;
        backgroudSprite = new Sprite(tm.getBarBackground());
        backgroudSprite.setPosition(position.x, position.y);
        backgroudSprite.setSize(barLength, barHeight);


        manaSprites = new Sprite[MAX_MANA_NUMBER];

        for (int i = 0; i < MAX_MANA_NUMBER; i++) {
            manaSprites[i] = new Sprite(tm.getMana());
            float manaX = position.x + barHeight * i * 5 / 9;
            manaSprites[i].setPosition(manaX, position.y);
            manaSprites[i].setSize(barHeight, barHeight);
        }

    }

    public void draw(SpriteBatch batch) {
        backgroudSprite.draw(batch);
        for (int i = 0; i < manaNumber; i++) {
            manaSprites[i].draw(batch);
        }

    }

    public void setMana(int newMana) {
        manaNumber = newMana;
    }
}
