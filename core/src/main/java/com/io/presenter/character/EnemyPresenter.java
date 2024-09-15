package com.io.presenter.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.EnemyView;

public class EnemyPresenter implements CharacterPresenter {

    private final SoundManager sm;
    private final CoordinatesManager cm;

    private final EnemyView enemyView;
    private BoardPosition boardPosition;

    private boolean isMoving;
    private float movementTime = 0;
    private float stateTime = 0;
    private int stateNumber = 0;
    private Vector2 startPosition;
    private Vector2 targetPosition;

    private final int maxHealth;
    private int health;


    public EnemyPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition startBoardPosition, int maxHealth) {
        this.sm = sm;
        this.cm = cm;

        isMoving = false;
        boardPosition = startBoardPosition;

        Vector2 position = cm.calculatePosition(boardPosition);
        enemyView = new EnemyView(tm, position, cm.getTileSize());

        this.maxHealth = maxHealth;
        health = maxHealth;
        enemyView.changeHealth(1);
    }

    public void startMoveAnimation(BoardPosition targetBoardPosition) {
        if (targetBoardPosition == boardPosition) return;
        isMoving = true;
        movementTime = 0;

        sm.playMoveSound();

        startPosition = cm.calculatePosition(boardPosition);
        targetPosition = cm.calculatePosition(targetBoardPosition);
        boardPosition = targetBoardPosition;
    }

    public void update(BoardPosition position) {
        if (position != boardPosition) startMoveAnimation(position);
        updateState();
    }

    public void updatePosition() {
        movementTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, movementTime / animationDuration);

        float currentX = startPosition.x + (targetPosition.x - startPosition.x) * progress;
        float currentY = startPosition.y + (targetPosition.y - startPosition.y) * progress;
        Vector2 currentPosition = new Vector2(currentX, currentY);
        enemyView.setPosition(currentPosition);

        if (progress >= 1.0f) {
            isMoving = false;
        }
    }

    private void updateState() {
        stateTime += Gdx.graphics.getDeltaTime();
        if (stateNumber == 0) {
            if (stateTime >= 3.0f) {
                stateNumber = 1;
                stateTime = 0f;
            }
        } else {
            if (stateTime >= 0.3f) {
                stateNumber = 0;
                stateTime = 0f;
            }
        }
    }

    public void render(SpriteBatch batch) {
        enemyView.draw(batch);
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setHealth(int value) {
        health = value;
        enemyView.changeHealth((float) health / maxHealth);
        sm.playRoarSound();
    }

}
