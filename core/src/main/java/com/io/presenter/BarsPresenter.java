package com.io.presenter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.bars_buttons.HealthBarView;
import com.io.view.bars_buttons.ManaBarView;
import com.io.view.bars_buttons.TourButton;

public class BarsPresenter {
    private final HealthBarView healthBar;
    private final ManaBarView manaBar;
    private final TourButton tourButton;

    public BarsPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm) {
        float tileSize = cm.getTileSize();
        float boardX = cm.getBoardX();
        float boardY = cm.getBoardY();
        float rows = cm.getRows();
        float cols = cm.getCols();

        float heartBarY = boardY + (rows + 0.9f) * tileSize;
        float barHeight = tileSize * 9 / 16;
        Vector2 heartPosition = new Vector2(boardX, heartBarY);
        healthBar = new HealthBarView(tm, heartPosition, barHeight);

        float manaBarY = boardY + (rows + 0.2f) * tileSize;
        Vector2 manaPosition = new Vector2(boardX, manaBarY);
        manaBar = new ManaBarView(tm, manaPosition, barHeight);

        float tourButtonX = boardX + (cols - 1) * tileSize;
        Vector2 tourButtonPosition = new Vector2(tourButtonX, manaBarY);
        tourButton = new TourButton(tm, tourButtonPosition, tileSize);
    }

    public void setHealth(int newHealth){
        healthBar.setHealth(newHealth);
    }

    public void setMana(int newMana){
        manaBar.setMana(newMana);
    }

    public void render(SpriteBatch batch){
        healthBar.draw(batch);
        manaBar.draw(batch);
    }

    public TourButton getTourButton(){
        return tourButton;
    }
}
