package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.bars_buttons.TourButton;

public class ButtonsPresenter {
    private final SoundManager sm;
    private final GamePresenter gp;

    private final TourButton tourButton;

    private boolean isActive;
    private float elapsedTime;

    public ButtonsPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, GamePresenter gp) {
        this.sm = sm;
        this.gp = gp;

        float rows = cm.getRows();
        float cols = cm.getCols();
        float boardX = cm.getBoardX();
        float boardY = cm.getBoardY();
        float tileSize = cm.getTileSize();

        float manaBarY = boardY + (rows + 0.2f) * tileSize;

        float tourButtonX = boardX + (cols - 1) * tileSize;
        Vector2 tourButtonPosition = new Vector2(tourButtonX, manaBarY);
        tourButton = new TourButton(tm, tourButtonPosition, tileSize);

        isActive = false;
    }


    void handleInput(Vector2 mousePosition) {
        if (isActive) {
            updateAnimation();
        } else {
            tourButton.setTexture(0);
            if (tourButton.contains(mousePosition)) {
                tourButton.setTexture(1);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    sm.playSwordSound();
                    startAnimation();
                    gp.endTurn();
                }
            }
        }
    }

    private void startAnimation() {
        isActive = true;
        elapsedTime = 0;
    }

    private void updateAnimation() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, elapsedTime / animationDuration);

        if (progress > 0.75f) tourButton.setTexture(1);
        else if (progress > 0.5f) tourButton.setTexture(2);
        else if (progress > 0.25f) tourButton.setTexture(3);
        else tourButton.setTexture(2);

        if (progress >= 1.0f) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        tourButton.draw(batch);
    }
}
