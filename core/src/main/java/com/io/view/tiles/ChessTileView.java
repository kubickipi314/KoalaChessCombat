package com.io.view.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ChessTileView {
    private final Texture normalTexture;
    private final Texture selectedTexture;
    private final Sprite figureSprite;
    private final Vector2 normalPosition;
    private final Vector2 markedPosition;

    private final Sprite damageSprite;
    private final Sprite costSprite;


    public ChessTileView(Texture normal, Texture selected, Vector2 position, float size) {
        figureSprite = new Sprite(normal);
        figureSprite.setPosition(position.x, position.y);
        figureSprite.setSize(size, 2 * size);

        Color blueColor = new Color(0.4f, 0.6f, 1.0f, 1.0f);
        Color redColor = new Color(1.0f, 0.4f, 0.4f, 1.0f);

        costSprite = new Sprite(normal);
        costSprite.setPosition(position.x, position.y);
        costSprite.setSize(size, size);
        costSprite.setColor(blueColor);

        damageSprite = new Sprite(normal);
        damageSprite.setPosition(position.x + size * 0.45f, position.y);
        damageSprite.setSize(size, size);
        damageSprite.setColor(redColor);

        this.normalTexture = normal;
        this.selectedTexture = selected;

        this.normalPosition = new Vector2(position.x, position.y);
        this.markedPosition = new Vector2(position.x, position.y + size / 8);
    }

    public void select() {
        figureSprite.setTexture(selectedTexture);
    }

    public void unselect() {
        figureSprite.setTexture(normalTexture);
    }

    public void draw(SpriteBatch batch) {
        figureSprite.draw(batch);
        damageSprite.draw(batch);
        costSprite.draw(batch);
    }

    public boolean contains(Vector2 mousePosition) {
        return figureSprite.getBoundingRectangle().contains(mousePosition.x, mousePosition.y);
    }

    public void unmark() {
        figureSprite.setPosition(normalPosition.x, normalPosition.y);
    }

    public void mark() {
        figureSprite.setPosition(markedPosition.x, markedPosition.y);
    }

    public void setDamage(Texture digitTexture) {
        damageSprite.setTexture(digitTexture);
    }

    public void setCost(Texture digitTexture) {
        costSprite.setTexture(digitTexture);
    }
}
