package com.io.presenter;

import com.badlogic.gdx.Gdx;

public class CoordinatesManager {
    private final int rows;
    private final int cols;
    private int chessNumber;
    private final float windowWidth;
    private final float tileSize;
    private final float boardX;
    private final float boardY;
    private final float barHeight;

    public CoordinatesManager(int rows, int cols) {
        windowWidth = Gdx.graphics.getWidth();
        float windowHeight = Gdx.graphics.getHeight();

        chessNumber = 5;

        this.rows = rows;
        this.cols = cols;

        tileSize = Math.min(windowHeight / (rows + 4), windowWidth / Math.max((cols + 2), 7));
        float boardWidth = tileSize * cols;
        float boardHeight = tileSize * rows;

        boardX = (windowWidth - boardWidth) / 2;
        boardY = (windowHeight - boardHeight) / 2;

        barHeight = tileSize * 9 / 16;
    }

    public void setChessNumber(int chessNumber) {
        this.chessNumber = chessNumber;
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

    public float getChessBoardX() {
        return (windowWidth - tileSize * chessNumber) / 2;
    }

    public float getChessBoardY() {
        return boardY - 2 * tileSize;
    }

    public float getManaBarY() {
        return boardY + (rows + 0.2f) * tileSize;
    }

    public float getHealthBarY() {
        return boardY + (rows + 0.9f) * tileSize;
    }

    public float getTileSize() {
        return tileSize;
    }

    public float getBarHeight() {
        return barHeight;
    }
}
