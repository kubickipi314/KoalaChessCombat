package com.io.view.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ChessTileView {
    private final Texture normalTexture;
    private final Texture selectedTexture;
    private final Sprite figureSprite;

    public ChessTileView(Texture normal, Texture selected, Vector2 position, float size){
        figureSprite = new Sprite(normal);
        figureSprite.setPosition(position.x,position.y);
        figureSprite.setSize(size,2 * size);

        this.normalTexture = normal;
        this.selectedTexture = selected;
    }

    public void select(){
        figureSprite.setTexture(selectedTexture);
    }

    public void unselect(){
        figureSprite.setTexture(normalTexture);
    }

    public void draw(SpriteBatch batch){
        figureSprite.draw(batch);
    }

    public boolean contains(Vector2 mousePosition) {
        return figureSprite.getBoundingRectangle().contains(mousePosition.x,mousePosition.y);
    }
}
