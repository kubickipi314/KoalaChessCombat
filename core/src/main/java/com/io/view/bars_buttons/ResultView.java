package com.io.view.bars_buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ResultView {
    private final Sprite result;

    public ResultView(Texture texture, Vector2 position, float size) {
        result = new Sprite(texture);
        result.setPosition(position.x, position.y);
        result.setSize(size * 2, size);
    }

    public void setTexture(Texture texture) {
        result.setTexture(texture);
    }

    public void draw(SpriteBatch batch) {
        result.draw(batch);
    }

}