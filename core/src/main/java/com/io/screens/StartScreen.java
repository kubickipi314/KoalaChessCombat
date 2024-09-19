package com.io.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.io.menu.presenters.StartPresenter;

public class StartScreen implements Screen {

    private final StartPresenter startPresenter;

    public StartScreen(StartPresenter startPresenter) {
        this.startPresenter = startPresenter;
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0.12f, 0.12f, 0.12f, 1);
    }

    @Override
    public void render(float delta) {
        startPresenter.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        startPresenter.render();
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
