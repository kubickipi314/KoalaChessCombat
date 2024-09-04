package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.bars_buttons.HealthBarView;
import com.io.view.bars_buttons.ManaBarView;
import com.io.view.characters.PlayerView;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;

public class PlayerPresenter {

    private int mana = 5;
    private int health = 8;
    private final int MAX_MANA = 12;
    private final int MAX_HEALTH = 10;

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

    private final PlayerView playerView;
    private HealthBarView healthBar;
    private ManaBarView manaBar;


    public PlayerPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm) {
        boardX = cm.getBoardX();
        boardY = cm.getBoardY();
        tileSize = cm.getTileSize();
        this.sm = sm;

        posX = 2;
        posY = 1;
        isActive = false;

        float x = boardX + posX * tileSize;
        float y = boardY + posY * tileSize;
        Vector2 position = new Vector2(x, y);
        playerView = new PlayerView(tm, position, tileSize);
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

        playerView.changePosition(currentPosition);

        if (progress >= 1.0f) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        playerView.draw(batch);
    }

    public boolean isActive() {
        return isActive;
    }


    public void increaseMana(int i) {
        mana = Math.min(mana + i, MAX_MANA);
        manaBar.setMana(mana);
    }

    public void decreaseMana(int i){
        mana = Math.max(mana - i, 0);
        manaBar.setMana(mana);
    }

    public void decreaseHealth(int i) {
        health = Math.max(health - i, 0);
        healthBar.setHealth(health);
    }

    public void setManaBar(ManaBarView manaBar) {
        this.manaBar = manaBar;
    }

    public void setHealthBar(HealthBarView healthBar) {
        this.healthBar = healthBar;
    }
}
