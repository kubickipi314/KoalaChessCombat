package com.io.view.bars_buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

public class TourButton {
    Sprite button;

    public TourButton(TextureManager tm, Vector2 position, float buttonWidth) {
        button = new Sprite(tm.getTourButton());
        button.setPosition(position.x, position.y);
        button.setSize(buttonWidth, buttonWidth * 1.25f);
    }

    public void draw(SpriteBatch batch) {
        button.draw(batch);
    }

    public boolean contains(Vector2 mousePosition) {
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;
        return button.getBoundingRectangle().contains(mouseX, mouseY);
    }
}
