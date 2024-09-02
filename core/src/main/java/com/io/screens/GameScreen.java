package com.io.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.io.presenter.GamePresenter;
import com.io.presenter.GamePresenterFactory;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private GamePresenter gamePresenter;

    @Override
    public void show() {
        GamePresenterFactory gpFactory = new GamePresenterFactory(5, 7);
        gamePresenter = gpFactory.getGamePresenter();
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
