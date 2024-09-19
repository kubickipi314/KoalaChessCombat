package com.io;

import com.badlogic.gdx.Game;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    @Override
    public void create() {
//        TurnService ts = new TurnService();
//        GameService gs = new GameService();
//        GamePresenter gamePresenter = new GamePresenter();
//        gs.init(ts, gamePresenter);
//        gamePresenter.init(gs);
//        setScreen(new GameScreen(gamePresenter));
//        gs.startGame();

        Coordinator coordinator = new Coordinator(this);
        coordinator.setStartScreen();
    }
}
