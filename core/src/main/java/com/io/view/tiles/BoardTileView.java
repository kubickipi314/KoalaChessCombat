package com.io.view.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

public class BoardTileView {
    private final Vector2 position;
    private boolean isMarked;
    private boolean isAvailable;

    private final Sprite tileSprite;
    private final Sprite availableSprite;
    private final Sprite markedSprie;

    public BoardTileView(TextureManager tm, Vector2 position, float size) {
        this.position = position;
        Texture markedCover = tm.getMarkedCover();
        Texture availableCover = tm.getAvailableCover();

        tileSprite = new Sprite(tm.getNormalTile());
        tileSprite.setPosition(position.x, position.y);
        tileSprite.setSize(size, size);

        availableSprite = new Sprite(tileSprite);
        availableSprite.setTexture(availableCover);

        markedSprie = new Sprite(tileSprite);
        markedSprie.setTexture(markedCover);

    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void switchAvailable() {
        this.isAvailable = !this.isAvailable;
    }

    public void setMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

    public void draw(SpriteBatch batch) {
        tileSprite.draw(batch);
        if (isMarked) markedSprie.draw(batch);
        if (isAvailable) availableSprite.draw(batch);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean contains(Vector2 point) {
        return tileSprite.getBoundingRectangle().contains(point.x, point.y);
    }
}
