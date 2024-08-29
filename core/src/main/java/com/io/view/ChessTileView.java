package com.io.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ChessTileView {
    private Texture normalTexture;
    private Texture selectedTexture;

    private Sprite tileSprite;
    private Sprite figureSprite;

    public ChessTileView(Texture tile, Texture normal, Texture selected, Vector2 position, float size){
        tileSprite = new Sprite(tile);
        tileSprite.setPosition(position.x,position.y);
        tileSprite.setSize(size,2 * size);

        figureSprite = new Sprite(normal);
        figureSprite.setPosition(position.x,position.y);
        figureSprite.setSize(size,2 * size);

        this.normalTexture = normal;
        this.selectedTexture = selected;
    }

    public void changePosition(Vector2 position){
        tileSprite.setPosition(position.x, position.y);
    }

    public void changeSize(float size){
        tileSprite.setSize(size,size);
    }

    public void select(){
        figureSprite.setTexture(selectedTexture);
        System.out.println("selectedTexture...");
    }

    public void unselect(){
        figureSprite.setTexture(normalTexture);
    }

    public void draw(SpriteBatch batch){
        //tileSprite.draw(batch);
        figureSprite.draw(batch);
    }

    public boolean contains(float mouseX, float mouseY) {
        return figureSprite.getBoundingRectangle().contains(mouseX,mouseY);
    }
}
