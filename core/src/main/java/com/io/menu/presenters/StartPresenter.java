package com.io.menu.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.Coordinator;
import com.io.menu.view.MenuButton;
import com.io.menu.view.MenuSoundManager;
import com.io.menu.view.MenuTextureManager;

public class StartPresenter {
    private final Coordinator coordinator;
    private final MenuSoundManager sm;
    private final SpriteBatch batch;
    private final MenuButton playButton;
    private final MenuButton quitButton;
    private final MenuButton playerButton;
    private final float windowHeight;

    public StartPresenter(Coordinator coordinator) {
        this.coordinator = coordinator;

        batch = new SpriteBatch();
        MenuTextureManager tm = new MenuTextureManager();
        sm = new MenuSoundManager();

        float windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        float tileSize = windowHeight / 7;
        float mainY = tileSize / 2;

        Vector2 quitPosition = new Vector2(windowWidth / 2 - tileSize, mainY);
        quitButton = new MenuButton(tm.getQuitButton(), quitPosition, tileSize, 2 * tileSize);

        Vector2 playPosition = new Vector2(windowWidth / 2 - 1.5f * tileSize, mainY + tileSize);
        playButton = new MenuButton(tm.getPlayButton(), playPosition, tileSize, 3 * tileSize);

        Vector2 characterPosition = new Vector2(windowWidth / 2 - tileSize, mainY + tileSize * 2.2f);
        playerButton = new MenuButton(tm.getPlayer(), characterPosition, 2 * tileSize, 2 * tileSize);
    }

    public void update() {
        quitButton.unmark();
        playButton.unmark();
        playerButton.unmark();

        Vector2 mousePosition = getMousePosition();

        if (quitButton.contains(mousePosition)) {
            quitButton.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("QUIT!!!");
                sm.playSelectSound();
                coordinator.quit();
            }
        } else if (playButton.contains(mousePosition)) {
            playButton.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("PLAY!!!");
                sm.playSelectSound();
                coordinator.setMenuScreen();
            }
        } else if (playerButton.contains(mousePosition)) {
            playerButton.mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("Choose player");
                sm.playSelectSound();
            }
        }
    }

    public void render() {
        batch.begin();
        quitButton.draw(batch);
        playButton.draw(batch);
        playerButton.draw(batch);
        batch.end();
    }

    private Vector2 getMousePosition() {
        Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        float mouseX = mouseWorldCoords.x;
        float mouseY = (windowHeight - mouseWorldCoords.y);
        return new Vector2(mouseX, mouseY);
    }
}
