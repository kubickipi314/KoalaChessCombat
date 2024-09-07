package com.io;

import com.badlogic.gdx.Game;
import com.io.presenter.GamePresenter;
import com.io.screens.GameScreen;
import com.io.service.GameService;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    @Override
    public void create() {
        GameService gs = new GameService();
        GamePresenter gp = new GamePresenter(gs);
        setScreen(new GameScreen(gp));
    }
}
