package com.io.view.bars_buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

import java.util.List;

public class TourButton {
    private final Sprite button;
    private final List<Texture> textures;
    private final int buttonState = 0;
    public TourButton(TextureManager tm, Vector2 position, float buttonWidth) {
        textures = tm.getTourButton();
        button = new Sprite(textures.get(buttonState));
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
    public void unmark() {
        button.setTexture(textures.get(0));
    }
    public void mark() {
        button.setTexture(textures.get(1));
    }
}
