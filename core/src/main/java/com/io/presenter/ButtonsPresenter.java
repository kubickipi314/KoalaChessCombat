package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.GameResult;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.bars_buttons.ResultView;
import com.io.view.bars_buttons.TourButton;

import static com.io.core.GameResult.WIN;

public class ButtonsPresenter {
    private final SoundManager sm;
    private final TextureManager tm;
    private final GamePresenter gamePresenter;
    private final TourButton tourButton;
    private final ResultView resultView;
    private boolean isActive;
    private float elapsedTime;
    private boolean isResultShown;

    public ButtonsPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, GamePresenter gamePresenter) {
        this.tm = tm;
        this.sm = sm;
        this.gamePresenter = gamePresenter;
        float tileSize = cm.getTileSize();
        float boardX = cm.getBoardX();
        float cols = cm.getCols();

        float tourButtonX = boardX + (cols - 1) * tileSize;
        Vector2 tourButtonPosition = new Vector2(tourButtonX, cm.getManaBarY());
        tourButton = new TourButton(tm, tourButtonPosition, tileSize);

        Vector2 resultPosition = new Vector2(cm.getBoardX(), cm.getBoardY());
        resultView = new ResultView(tm.getResult(WIN), resultPosition, tileSize);

        isActive = false;
        isResultShown = false;
    }


    void handleInput(Vector2 mousePosition) {
        if (!isActive) {
            tourButton.setTexture(0);
            if (tourButton.contains(mousePosition)) {
                tourButton.setTexture(1);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    sm.playSwordSound();
                    startAnimation();
                    gamePresenter.endTurn();
                }
            }
        }
    }

    private void startAnimation() {
        isActive = true;
        elapsedTime = 0;
    }

    public void update() {
        if (isActive) {
            updateAnimation();
        }
    }

    private void updateAnimation() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, elapsedTime / animationDuration);

        if (progress > 0.8f) tourButton.setTexture(1);
        else if (progress > 0.6f) tourButton.setTexture(2);
        else if (progress > 0.2f) tourButton.setTexture(3);
        else tourButton.setTexture(2);

        if (progress >= 1.0f) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        tourButton.draw(batch);
        if (isResultShown) resultView.draw(batch);
    }

    public void showResult(GameResult gameResult) {
        isResultShown = true;
        resultView.setTexture(tm.getResult(gameResult));
    }
}
