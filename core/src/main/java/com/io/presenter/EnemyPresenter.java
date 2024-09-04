package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.characters.EnemyView;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;

public class EnemyPresenter {
    private int health = 5;
    private final int MAX_HEALTH = 5;

    private int posX;
    private int posY;

    private boolean isActive;
    private float elapsedTime = 0;
    private Vector2 startPosition;
    private Vector2 targetPosition;
    private final SoundManager sm;

    private final float boardX;
    private final float boardY;
    private final float tileSize;

    private final EnemyView enemyView;
    private int moveState = 0;


    public EnemyPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm) {
        boardX = cm.getBoardX();
        boardY = cm.getBoardY();
        tileSize = cm.getTileSize();
        this.sm = sm;

        posX = 3;
        posY = 3;
        isActive = false;

        float x = boardX + posX * tileSize;
        float y = boardY + posY * tileSize;
        Vector2 position = new Vector2(x, y);
        enemyView = new EnemyView(tm, position, tileSize);
    }

    public void startMoveAnimation(int targetCol, int targetRow) {
        isActive = true;
        elapsedTime = 0;

        sm.playMoveSound();

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
        elapsedTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, elapsedTime / animationDuration);

        float currentX = startPosition.x + (targetPosition.x - startPosition.x) * progress;
        float currentY = startPosition.y + (targetPosition.y - startPosition.y) * progress;

        Vector2 currentPosition = new Vector2(currentX, currentY);

        enemyView.changePosition(currentPosition);

        if (progress >= 1.0f) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        enemyView.draw(batch);
    }

    public boolean isActive() {
        return isActive;
    }

    public void decreaseHealth(int value) {
        health = Math.max(health - value, 0);
        enemyView.changeHealth((float) health / MAX_HEALTH);
        sm.playRoarSound();
    }

    public void move() {
        if (moveState == 0) startMoveAnimation(posX + 1, posY);
        if (moveState == 1) startMoveAnimation(posX, posY - 1);
        if (moveState == 2) startMoveAnimation(posX - 1, posY);
        if (moveState == 3) startMoveAnimation(posX, posY + 1);
        moveState = (moveState + 1) % 4;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

}
