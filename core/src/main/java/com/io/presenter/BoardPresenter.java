package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.tiles.BoardTileView;
import com.io.view.assets_managers.TextureManager;

import java.util.List;

public class BoardPresenter {
    private final BoardTileView[][] board;
    private PlayerPresenter player;
    private EnemyPresenter enemy;

    private int actualRow;
    private int actualCol;

    private final float boardX;
    private final float boardY;
    private final int rows;
    private final int cols;
    private final float boardWidth;
    private final float boardHeight;
    private List<int[]> availableTiles;

    public BoardPresenter(TextureManager tm, CoordinatesManager cm) {
        rows = cm.getRows();
        cols = cm.getCols();

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

    public void setPlayer(PlayerPresenter player) {
        this.player = player;
    }

    public void setEnemy(EnemyPresenter enemy) {
        this.enemy = enemy;
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
                player.startMoveAnimation(actualCol, actualRow);
                player.decreaseMana(2);
                if (enemy.getPosX() == actualCol && enemy.getPosY() == actualRow) {
                    enemy.move();
                    enemy.decreaseHealth(1);
                }
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

    public void setAvailableTiles(List<int[]> availableTiles) {
        if (this.availableTiles != null) {
            for (int[] pair : this.availableTiles) {
                board[pair[0]][pair[1]].setAvailable(false);
            }
        }
        this.availableTiles = availableTiles;
        for (int[] pair : this.availableTiles) {
            board[pair[0]][pair[1]].setAvailable(true);
        }
    }
}
