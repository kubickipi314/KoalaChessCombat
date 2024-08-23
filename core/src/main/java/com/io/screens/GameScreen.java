package com.io.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.io.view.GameView;
import com.io.viewmodel.GameViewModel;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private GameViewModel gameViewModel;
    private GameView gameView;

    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void show() {
        System.out.println("show...");
        gameView = new GameView();
        gameViewModel = new GameViewModel(gameView);
    }

    @Override
    public void render(float delta) {
        gameViewModel.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameViewModel.render();

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
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
