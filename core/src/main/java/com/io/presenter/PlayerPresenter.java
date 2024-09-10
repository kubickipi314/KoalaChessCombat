package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.PlayerView;

public class PlayerPresenter {

    private int posX;
    private int posY;

    private boolean isMoving;
    private float movementTime = 0;
    private float stateTime = 0;
    private int stateNumber = 0;
    private Vector2 startPosition;
    private Vector2 targetPosition;
    private final SoundManager sm;

    private final float boardX;
    private final float boardY;
    private final float tileSize;

    private final PlayerView playerView;


    public PlayerPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition start) {
        boardX = cm.getBoardX();
        boardY = cm.getBoardY();
        tileSize = cm.getTileSize();
        this.sm = sm;

        posX = start.x();
        posY = start.y();
        isMoving = false;

        float x = boardX + posX * tileSize;
        float y = boardY + posY * tileSize;
        Vector2 position = new Vector2(x, y);
        playerView = new PlayerView(tm, position, tileSize);
    }

    public void update(int col, int row) {
        if (col != posX || row != posY) startMoveAnimation(row, col);
        updateState();
    }

    public void startMoveAnimation(int targetRow, int targetCol) {
        if (targetCol == posX && targetRow == posY) return;
        isMoving = true;
        movementTime = 0;

        sm.playMoveSound();
        playerView.setTexture(2);

        float startX = boardX + posX * tileSize;
        float startY = boardY + posY * tileSize;

        float targetX = boardX + targetCol * tileSize;
        float targetY = boardY + targetRow * tileSize;

        startPosition = new Vector2(startX, startY);
        targetPosition = new Vector2(targetX, targetY);

        posX = targetCol;
        posY = targetRow;
    }

    public void updatePosition() {
        movementTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, movementTime / animationDuration);

        float currentX = startPosition.x + (targetPosition.x - startPosition.x) * progress;
        float currentY = startPosition.y + (targetPosition.y - startPosition.y) * progress;

        Vector2 currentPosition = new Vector2(currentX, currentY);

        playerView.setPosition(currentPosition);

        if (progress >= 1.0f) {
            isMoving = false;
            playerView.setTexture(stateNumber);
        }
    }

    private void updateState() {
        stateTime += Gdx.graphics.getDeltaTime();
        if (stateNumber == 0) {
            if (stateTime >= 3.0f) {
                stateNumber = 1;
                playerView.setTexture(stateNumber);
                stateTime = 0f;
            }
        } else {
            if (stateTime >= 0.3f) {
                stateNumber = 0;
                playerView.setTexture(stateNumber);
                stateTime = 0f;
            }
        }
    }

    public void render(SpriteBatch batch) {
        playerView.draw(batch);
    }

    public boolean isMoving() {
        return isMoving;
    }
}
