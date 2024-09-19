package com.io.menu.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.Coordinator;
import com.io.menu.view.MenuButton;
import com.io.menu.view.MenuSoundManager;
import com.io.menu.view.MenuTextureManager;

public class MenuPresenter {
    private final Coordinator coordinator;
    private final MenuSoundManager sm;
    private final SpriteBatch batch;
    private final MenuButton backButton;
    private final MenuButton levelButton;
    private final float windowHeight;

    public MenuPresenter(Coordinator coordinator) {
        this.coordinator = coordinator;

        batch = new SpriteBatch();
        MenuTextureManager tm = new MenuTextureManager();
        sm = new MenuSoundManager();

        float windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        float tileSize = windowHeight / 5;
        Vector2 playPosition = new Vector2(tileSize / 2, tileSize / 2);
        backButton = new MenuButton(tm.getBackButton(), playPosition, tileSize, tileSize);

        Vector2 characterPosition = new Vector2(windowWidth / 2 - tileSize * 1.5f, tileSize * 1.5f);
        levelButton = new MenuButton(tm.getLevel(), characterPosition, 3 * tileSize, 3 * tileSize);
    }

    public void update() {
        backButton.unmark();
        levelButton.unmark();

        Vector2 mousePosition = getMousePosition();
        if (backButton.contains(mousePosition)) {
            backButton.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("Go back");
                sm.playSelectSound();
                coordinator.setStartScreen();
            }
        }

        if (levelButton.contains(mousePosition)) {
            levelButton.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("Start game level");
                sm.playSelectSound();
                coordinator.setGameScreen();
            }
        }
    }

    public void render() {
        batch.begin();
        backButton.draw(batch);
        levelButton.draw(batch);
        batch.end();
    }

    private Vector2 getMousePosition() {
        Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        float mouseX = mouseWorldCoords.x;
        float mouseY = (windowHeight - mouseWorldCoords.y);
        return new Vector2(mouseX, mouseY);
    }
}
