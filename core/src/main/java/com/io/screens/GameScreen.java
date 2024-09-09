package com.io.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.io.presenter.GamePresenter;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private final GamePresenter gamePresenter;

    public GameScreen(GamePresenter gamePresenter) {
        this.gamePresenter = gamePresenter;
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1);
    }

    @Override
    public void render(float delta) {
        gamePresenter.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gamePresenter.render();
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
