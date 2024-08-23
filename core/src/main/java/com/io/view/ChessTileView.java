package com.io.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ChessTileView {
    private Texture normalTexture;
    private Texture chooseTexture;

    private Sprite tileSprite;

    public ChessTileView(Texture normalTile, Texture chooseTexture, Vector2 position, float size){
        tileSprite = new Sprite(normalTile);
        tileSprite.setPosition(position.x,position.y);
        tileSprite.setSize(size,size);

        this.normalTexture = normalTile;
        this.chooseTexture = chooseTexture;
    }

    public void changePosition(Vector2 position){
        tileSprite.setPosition(position.x, position.y);
    }

    public void changeSize(float size){
        tileSprite.setSize(size,size);
    }

    public void choose(){
        tileSprite.setTexture(chooseTexture);
    }

    public void unchoose(){
        tileSprite.setTexture(normalTexture);
    }

    public void draw(SpriteBatch batch){
        tileSprite.draw(batch);
    }
}
