package com.io.presenter.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.Coordinator;
import com.io.view.menu.MenuButton;
import com.io.view.menu.MenuLevelText;
import com.io.managers.menu.MenuSoundManager;
import com.io.managers.menu.MenuTextureManager;

import java.util.ArrayList;
import java.util.List;

public class MenuPresenter {
    //TODO: keeping and managing list of snapshot (exture,id)
    private final Coordinator coordinator;
    private final MenuSoundManager sm;
    private final MenuTextureManager tm;
    private final SpriteBatch batch;
    private final MenuButton backButton;
    private final MenuLevelText levelText;
    private final MenuButton levelPicture;
    private final MenuButton leftArrow;
    private final MenuButton rightArrow;
    private final List<MenuButton> buttons;
    private final float windowHeight;

    public MenuPresenter(Coordinator coordinator) {
        this.coordinator = coordinator;

        batch = new SpriteBatch();
        tm = new MenuTextureManager();
        sm = new MenuSoundManager();

        float windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        float tileSize = windowHeight / 5;

        Vector2 levelTextPosition = new Vector2(windowWidth / 2 - 65, windowHeight - 20); // TODO
        levelText = new MenuLevelText(levelTextPosition);

        Vector2 mainPosition = new Vector2(windowWidth / 2 - tileSize * 1.5f, tileSize * 1.5f);
        levelPicture = new MenuButton(tm.getLevel(coordinator.getCurrentLevel()), mainPosition, 3 * tileSize, 3 * tileSize);

        Vector2 playPosition = new Vector2(windowWidth / 2 - tileSize * 2.5f, tileSize * 0.5f);
        backButton = new MenuButton(tm.getBackButton(), playPosition, tileSize, tileSize);

        Vector2 rightPosition = new Vector2(windowWidth / 2 + tileSize * 1.5f, tileSize * 2.5f);
        rightArrow = new MenuButton(tm.getRightArrow(), rightPosition, tileSize, tileSize);

        Vector2 leftPosition = new Vector2(windowWidth / 2 - tileSize * 2.5f, tileSize * 2.5f);
        leftArrow = new MenuButton(tm.getLeftArrow(), leftPosition, tileSize, tileSize);

        buttons = new ArrayList<>();
        buttons.add(levelPicture);
        buttons.add(backButton);
        buttons.add(leftArrow);
        buttons.add(rightArrow);
        levelText.setValue("Level " + coordinator.getCurrentLevel());
    }

    public void update() {
        for (MenuButton button : buttons) button.unmark();

        Vector2 mousePosition = getMousePosition();

        for (MenuButton button : buttons) {
            if (button.contains(mousePosition)) button.mark();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (backButton.contains(mousePosition)) {
                System.out.println("Go back");
                sm.playSelectSound();
                coordinator.setStartScreen();
            } else if (levelPicture.contains(mousePosition)) {
                System.out.println("Start game level");
                sm.playSelectSound();
                coordinator.setGameScreen();
            } else if (leftArrow.contains(mousePosition)) {
                System.out.println("Go left");
                sm.playSelectSound();
                coordinator.previousLevel();
                long levelNumber = coordinator.getCurrentLevel();
                levelText.setValue("Level " + levelNumber);
                levelPicture.setTexture(tm.getLevel(levelNumber));
            } else if (rightArrow.contains(mousePosition)) {
                System.out.println("Go right");
                sm.playSelectSound();
                coordinator.nextLevel();
                long levelNumber = coordinator.getCurrentLevel();
                levelText.setValue("Level " + levelNumber);
                levelPicture.setTexture(tm.getLevel(levelNumber));
            }
        }
    }

    public void render() {
        batch.begin();
        for (MenuButton button : buttons) button.draw(batch);
        levelText.draw(batch);
        batch.end();
    }

    private Vector2 getMousePosition() {
        Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        float mouseX = mouseWorldCoords.x;
        float mouseY = (windowHeight - mouseWorldCoords.y);
        return new Vector2(mouseX, mouseY);
    }
}
