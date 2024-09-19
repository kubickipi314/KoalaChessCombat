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
    private final MenuButton levelPicture;
    private final MenuButton leftArrow;
    private final MenuButton rightArrow;
    private final float windowHeight;

    public MenuPresenter(Coordinator coordinator) {
        this.coordinator = coordinator;

        batch = new SpriteBatch();
        MenuTextureManager tm = new MenuTextureManager();
        sm = new MenuSoundManager();

        float windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        float tileSize = windowHeight / 5;

        Vector2 mainPosition = new Vector2(windowWidth / 2 - tileSize * 1.5f, tileSize * 1.5f);
        levelPicture = new MenuButton(tm.getLevel(), mainPosition, 3 * tileSize, 3 * tileSize);

        Vector2 playPosition = new Vector2(windowWidth / 2 - tileSize * 2.5f, tileSize * 0.5f);
        backButton = new MenuButton(tm.getBackButton(), playPosition, tileSize, tileSize);

        Vector2 rightPosition = new Vector2(windowWidth / 2 + tileSize * 1.5f, tileSize * 2.5f);
        leftArrow = new MenuButton(tm.getRightArrow(), rightPosition, tileSize, tileSize);

        Vector2 leftPosition = new Vector2(windowWidth / 2 - tileSize * 2.5f, tileSize * 2.5f);
        rightArrow = new MenuButton(tm.getLeftArrow(), leftPosition, tileSize, tileSize);

    }

    public void update() {
        backButton.unmark();
        levelPicture.unmark();
        rightArrow.unmark();
        leftArrow.unmark();

        Vector2 mousePosition = getMousePosition();
        if (backButton.contains(mousePosition)) {
            backButton.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("Go back");
                sm.playSelectSound();
                coordinator.setStartScreen();
            }
        } else if (levelPicture.contains(mousePosition)) {
            levelPicture.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("Start game level");
                sm.playSelectSound();
                coordinator.setGameScreen();
            }
        } else if (leftArrow.contains(mousePosition)) {
            leftArrow.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("Go left");
                sm.playSelectSound();

            }
        } else if (rightArrow.contains(mousePosition)) {
            rightArrow.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("Go right");
                sm.playSelectSound();

            }
        }
    }

    public void render() {
        batch.begin();
        backButton.draw(batch);
        leftArrow.draw(batch);
        rightArrow.draw(batch);
        levelPicture.draw(batch);
        batch.end();
    }

    private Vector2 getMousePosition() {
        Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        float mouseX = mouseWorldCoords.x;
        float mouseY = (windowHeight - mouseWorldCoords.y);
        return new Vector2(mouseX, mouseY);
    }
}
