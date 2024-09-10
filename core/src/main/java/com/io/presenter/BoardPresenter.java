package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.view.assets_managers.TextureManager;
import com.io.view.tiles.BoardTileView;

import java.util.List;

public class BoardPresenter {
    private final BoardTileView[][] board;

    private int actualRow;
    private int actualCol;

    private final float boardX;
    private final float boardY;
    private final int rows;
    private final int cols;
    private final float boardWidth;
    private final float boardHeight;
    private List<BoardPosition> availableTiles;
    private final GamePresenter gamePresenter;

    public BoardPresenter(TextureManager tm, CoordinatesManager cm, GamePresenter gamePresenter) {
        rows = cm.getRows();
        cols = cm.getCols();
        this.gamePresenter = gamePresenter;

        board = new BoardTileView[rows][cols];

        boardX = cm.getBoardX();
        boardY = cm.getBoardY();

        float tileSize = cm.getTileSize();

        boardWidth = tileSize * cols;
        boardHeight = tileSize * rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float x = boardX + col * tileSize;
                float y = boardY + row * tileSize;
                Vector2 position = new Vector2(x, y);
                board[row][col] = new BoardTileView(tm, position, tileSize);
            }
        }
    }

    public void handleInput(Vector2 mousePosition) {

        board[actualRow][actualCol].setMarked(false);

        if (isMouseInBoard(mousePosition)) {
            getActualTile(mousePosition);
            board[actualRow][actualCol].setMarked(true);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                board[actualRow][actualCol].switchAvailable();
            }
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("clicked");
                gamePresenter.movePlayer(new BoardPosition(actualCol, actualRow));
            }
        }
    }

    private boolean isMouseInBoard(Vector2 mousePosition) {
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;
        return mouseX >= boardX && mouseY >= boardY && mouseX <= boardX + boardWidth && mouseY <= boardY + boardHeight;
    }

    private void getActualTile(Vector2 mousePosition) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col].contains(mousePosition)) {
                    actualRow = row;
                    actualCol = col;
                    return;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col].draw(batch);
            }
        }
    }

    public void setAvailableTiles(List<BoardPosition> availableTiles) {
        if (this.availableTiles != null) {
            for (var pair : this.availableTiles) {
                board[pair.y()][pair.x()].setAvailable(false);
            }
        }
        this.availableTiles = availableTiles;
        for (var pair : this.availableTiles) {
            board[pair.y()][pair.x()].setAvailable(true);
        }
    }
}
