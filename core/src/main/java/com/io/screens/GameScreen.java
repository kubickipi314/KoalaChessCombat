package com.io.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.io.viewmodel.GameViewModel;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private GameViewModel gameViewModel;

    @Override
    public void show() {
        System.out.println("show...");

        //Gdx.graphics.setWindowedMode(1200, 900);

        gameViewModel = new GameViewModel();
    }

    @Override
    public void render(float delta) {
        gameViewModel.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameViewModel.render();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
