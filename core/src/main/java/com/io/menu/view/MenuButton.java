package com.io.menu.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class MenuButton {
    private final Sprite button;
    private final Texture normal;
    private final Texture marked;

    public MenuButton(List<Texture> textures, Vector2 position, float height, float width) {
        this.normal = textures.get(0);
        this.marked = textures.get(1);
        button = new Sprite(normal);
        button.setPosition(position.x, position.y);
        button.setSize(width, height);
    }

    public void draw(SpriteBatch batch) {
        button.draw(batch);
    }

    public boolean contains(Vector2 mousePosition) {
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;
        return button.getBoundingRectangle().contains(mouseX, mouseY);
    }

    public void mark() {
        button.setTexture(marked);
    }

    public void unmark() {
        button.setTexture(normal);
    }
}
