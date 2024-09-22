package com.io.view.game.bars_buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.game.TextureManager;

import java.util.List;

public class TurnButton {
    private final Sprite button;
    private final List<Texture> textures;

    public TurnButton(TextureManager tm, Vector2 position, float buttonWidth) {
        textures = tm.getTourButtons();
        button = new Sprite(textures.get(0));
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

    public void setTexture(int buttonState) {
        button.setTexture(textures.get(buttonState));
    }
}
