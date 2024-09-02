package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.characters.EnemyView;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;


public class EnemyPresenter {
    private final SoundManager sm;
    private int health = 8;
    private final int MAX_HEALTH = 8;

    private int posX;
    private int posY;
    private final EnemyView enemyView;

    private boolean isMoving;
    private float elapsedTime = 0;
    private Vector2 startPosition;
    private Vector2 targetPosition;

    private final float boardX;
    private final float boardY;
    private final float tileSize;

    public EnemyPresenter(TextureManager tm, SoundManager sm, float tileSize, float boardX, float boardY){
        posX = 5;
        posY = 4;
        isMoving = false;
        this.sm = sm;
        this.boardX = boardX;
        this.boardY = boardY;
        this.tileSize = tileSize;

        float x = boardX + posX * tileSize;
        float y = boardY + posY * tileSize;
        Vector2 position = new Vector2(x, y);
        enemyView = new EnemyView(tm, position, tileSize);
    }

    public void startMoveAnimation(int targetCol, int targetRow) {
        isMoving = true;
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

    public void updatePlayerPosition() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, elapsedTime / animationDuration);

        float currentX = startPosition.x + (targetPosition.x - startPosition.x) * progress;
        float currentY = startPosition.y + (targetPosition.y - startPosition.y) * progress;

        Vector2 currentPosition = new Vector2(currentX, currentY);

        enemyView.changePosition(currentPosition);

        if (progress >= 1.0f) {
            isMoving = false;
        }
    }

    public void render(SpriteBatch batch){
        enemyView.draw(batch);
    }

    public boolean isMoving(){
        return isMoving;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int i) {
        health = Math.min(health - i, 0);
    }
}