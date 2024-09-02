package com.io.presenter;

import com.badlogic.gdx.Gdx;

public class CoordinatesManager {
    private final int rows;
    private final int cols;
    private final int NUMBER_OF_CHESS = 5;
    private final float windowWidth;
    private final float windowHeight;
    private final float tileSize;
    private final float boardX;
    private final float boardY;

    public CoordinatesManager(int rows, int cols){
        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();

        this.rows = rows;
        this.cols = cols;

        tileSize = windowHeight / (rows + 4);
        float boardWidth = tileSize * cols;
        float boardHeight = tileSize * rows;

        boardX = (windowWidth - boardWidth) / 2;
        boardY = (windowHeight - boardHeight) / 2;
    }

    public float getBoardX() {
        return boardX;
    }
    public float getBoardY() {
        return boardY;
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public float getChessBoardX(){
        return (windowWidth - tileSize * NUMBER_OF_CHESS) / 2;
    }
    public float getChessBoardY(){
        return boardY - 2 * tileSize;
    }
    public float getTileSize() {
        return tileSize;
    }
}
