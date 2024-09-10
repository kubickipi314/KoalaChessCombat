package com.io.presenter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.CONST;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.bars_buttons.HealthBarView;
import com.io.view.bars_buttons.ManaBarView;
import com.io.view.bars_buttons.TourButton;

public class ButtonsPresenter {
    private final TourButton tourButton;
    private final SoundManager sm;
    private final GamePresenter gamePresenter;
    public ButtonsPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, GamePresenter gamePresenter){
        this.sm = sm;
        this.gamePresenter = gamePresenter;
        float tileSize = cm.getTileSize();
        float boardX = cm.getBoardX();
        float boardY = cm.getBoardY();
        float rows = cm.getRows();
        float cols = cm.getCols();

        float manaBarY = boardY + (rows + 0.2f) * tileSize;

        float tourButtonX = boardX + (cols - 1) * tileSize;
        Vector2 tourButtonPosition = new Vector2(tourButtonX, manaBarY);
        tourButton = new TourButton(tm, tourButtonPosition, tileSize);
    }


    void handleInput(Vector2 mousePosition) {
        tourButton.unmark();
        if (tourButton.contains(mousePosition)) {
            tourButton.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                gamePresenter.increaseMana();
                sm.playSwordSound();
                //enemy.move();
            }
        }
    }

    public void render(SpriteBatch batch) {
        tourButton.draw(batch);
    }
}
