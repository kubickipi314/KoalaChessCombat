package com.io.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class TourButton {
    Sprite button;
    public TourButton(TextureManager tm, Vector2 position, float buttonWidth){
        button = new Sprite(tm.getTourButton());
        button.setPosition(position.x, position.y);
        button.setSize(buttonWidth, buttonWidth*1.25f);
    }
    public void draw(SpriteBatch batch){
        button.draw(batch);
    }

    public boolean contains(float mouseX, float mouseY) {
        return button.getBoundingRectangle().contains(mouseX, mouseY);
    }
}
