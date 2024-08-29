package com.io.viewmodel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.BoardTileView;
import com.io.view.TextureManager;

public class BoardTile {
    private Vector2 position;
    private float size;
    private BoardTileView boardTileView;
    public BoardTile(TextureManager tm, Vector2 position, float size){
        this.position = position;

        boardTileView = new BoardTileView(tm, position, size);
    }

    public void update(Vector2 mousePosition) {

    }

    public void render(SpriteBatch batch) {
        boardTileView.draw(batch);
    }
}
