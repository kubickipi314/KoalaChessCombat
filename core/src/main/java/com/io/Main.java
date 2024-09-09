package com.io;

import com.badlogic.gdx.Game;
import com.io.presenter.GamePresenter;
import com.io.screens.GameScreen;
import com.io.service.GameService;
import com.io.service.TurnService;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    @Override
    public void create() {
        TurnService ts = new TurnService();
        GameService gs = new GameService(ts);
        GamePresenter gp = new GamePresenter(gs);
        setScreen(new GameScreen(gp));
    }
}
