package com.io.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BoardTileView {
    private Texture markedCover;
    private Texture availableCover;
    private boolean isMarked;
    private boolean isAvailable;

    private Sprite tileSprite;
    private Sprite availableSprite;
    private Sprite markedSprie;
    public BoardTileView(TextureManager tm, Vector2 position, float size){
        this.markedCover = tm.getMarkedCover();
        this.availableCover = tm.getAvailableCover();

        tileSprite = new Sprite(tm.getNormalTile());
        tileSprite.setPosition(position.x,position.y);
        tileSprite.setSize(size,size);

        availableSprite = new Sprite(tileSprite);
        availableSprite.setTexture(markedCover);

        markedSprie = new Sprite(tileSprite);
        markedSprie.setTexture(availableCover);


    }

    public void changePosition(Vector2 position){
        tileSprite.setPosition(position.x, position.y);
        availableSprite.setPosition(position.x, position.y);
        markedSprie.setPosition(position.x, position.y);
    }

    public void changeSize(float size){
        tileSprite.setSize(size,size);
        availableSprite.setSize(size,size);
        markedSprie.setSize(size,size);
    }

    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public void setMarked(boolean isMarked){
        this.isMarked = isMarked;
    }

    public void draw(SpriteBatch batch){
        System.out.println("draw :)");
        tileSprite.draw(batch);
        if (isMarked) markedSprie.draw(batch);
        if (isAvailable) availableSprite.draw(batch);
    }
}
