package com.io.view.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MenuLevelText {
    private final Vector2 textPosition;
    private final BitmapFont font = new BitmapFont();
    private String value = "";

    public MenuLevelText(Vector2 position) {
        this.textPosition = position;
        font.getData().setScale(3);
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, value, textPosition.x, textPosition.y);
    }

    public void setValue(String value) {
        this.value = value;
    }
}
