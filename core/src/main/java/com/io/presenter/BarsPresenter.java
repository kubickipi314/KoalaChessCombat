package com.io.presenter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;
import com.io.view.bars_buttons.HealthBarView;
import com.io.view.bars_buttons.ManaBarView;

public class BarsPresenter {
    private final HealthBarView healthBar;
    private final ManaBarView manaBar;

    public BarsPresenter(TextureManager tm, CoordinatesManager cm) {
        Vector2 healthPosition = new Vector2(cm.getBoardX(), cm.getHealthBarY());
        healthBar = new HealthBarView(tm, healthPosition, cm.getBarHeight());

        Vector2 manaPosition = new Vector2(cm.getBoardX(), cm.getManaBarY());
        manaBar = new ManaBarView(tm, manaPosition, cm.getBarHeight());
    }

    public void setHealth(int i) {
        healthBar.setHealth(i);
    }

    public void setMana(int i) {
        manaBar.setMana(i);
    }

    public void render(SpriteBatch batch) {
        healthBar.draw(batch);
        manaBar.draw(batch);
    }

}
