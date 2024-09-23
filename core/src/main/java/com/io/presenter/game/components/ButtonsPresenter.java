package com.io.presenter.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.GameResult;
import com.io.presenter.game.CoordinatesManager;
import com.io.presenter.game.GamePresenter;
import com.io.view.game.SoundManager;
import com.io.view.game.TextureManager;
import com.io.view.game.bars_buttons.ResultView;
import com.io.view.game.bars_buttons.GameButtonView;

import static com.io.core.GameResult.WIN;

public class ButtonsPresenter {
    private final SoundManager sm;
    private final TextureManager tm;
    private final GamePresenter gamePresenter;
    private final GameButtonView turnButtonView;
    private final GameButtonView exitButtonView;
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
        turnButtonView = new GameButtonView(tm.getTurnButton(), tourButtonPosition, tileSize, tileSize * 1.25f);

        Vector2 resultPosition = new Vector2(cm.getBoardX(), cm.getBoardY());
        resultView = new ResultView(tm.getResult(WIN), resultPosition, tileSize);

        float exitButtonX = boardX - tileSize * 1.25f;
        Vector2 exitButtonPosition = new Vector2(exitButtonX, cm.getManaBarY());
        exitButtonView = new GameButtonView(tm.getExitButton(), exitButtonPosition, tileSize, tileSize);

        isActive = false;
        isResultShown = false;
    }


    public void handleInput(Vector2 mousePosition) {
        if (!isActive) {
            turnButtonView.setTexture(0);
            if (turnButtonView.contains(mousePosition)) {
                turnButtonView.setTexture(1);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    sm.playSwordSound();
                    startAnimation();
                    gamePresenter.endTurn();
                }
            }
            exitButtonView.setTexture(0);
            if (exitButtonView.contains(mousePosition)) {
                exitButtonView.setTexture(1);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    sm.playSelectSound();
                    gamePresenter.exitGame();
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
            updateNextTurnAnimation();
        }
    }

    private void updateNextTurnAnimation() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, elapsedTime / animationDuration);

        if (progress > 0.8f) turnButtonView.setTexture(1);
        else if (progress > 0.6f) turnButtonView.setTexture(2);
        else if (progress > 0.2f) turnButtonView.setTexture(3);
        else turnButtonView.setTexture(2);

        if (progress >= 1.0f) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        turnButtonView.draw(batch);
        exitButtonView.draw(batch);
        if (isResultShown) resultView.draw(batch);
    }

    public void showResult(GameResult gameResult) {
        isResultShown = true;
        resultView.setTexture(tm.getResult(gameResult));
    }
}
