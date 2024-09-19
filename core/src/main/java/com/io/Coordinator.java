package com.io;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.io.menu.presenters.MenuPresenter;
import com.io.menu.presenters.StartPresenter;
import com.io.presenter.GamePresenter;
import com.io.screens.GameScreen;
import com.io.screens.MenuScreen;
import com.io.screens.StartScreen;
import com.io.service.GameService;
import com.io.service.TurnService;

public class Coordinator {
    //TODO: disposing resources after screen is not used

    //TODO: loading snapshots from db
    private final Game game;
    public Coordinator(Game game) {
        this.game = game;
    }

    public void setStartScreen() {
        StartPresenter startPresenter = new StartPresenter(this);
        StartScreen startScreen = new StartScreen(startPresenter);
        game.setScreen(startScreen);
    }

    public void setMenuScreen() {
        MenuPresenter menuPresenter = new MenuPresenter(this);
        MenuScreen menuScreen = new MenuScreen(menuPresenter);
        game.setScreen(menuScreen);
    }

    public void setGameScreen() {
        TurnService ts = new TurnService();
        GameService gs = new GameService();
        GamePresenter gamePresenter = new GamePresenter();
        gs.init(ts, gamePresenter);
        gamePresenter.init(gs, this);
        game.setScreen(new GameScreen(gamePresenter));
        gs.startGame();
    }

    public void quit() {
        Gdx.app.exit();
    }
}
