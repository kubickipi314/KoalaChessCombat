package com.io;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.io.core.snapshot.GameSnapshot;
import com.io.db.DatabaseEngine;
import com.io.menu.presenters.MenuPresenter;
import com.io.menu.presenters.StartPresenter;
import com.io.presenter.GamePresenter;
import com.io.screens.GameScreen;
import com.io.screens.MenuScreen;
import com.io.screens.StartScreen;
import com.io.service.GameService;
import com.io.service.SnapshotService;

public class Coordinator {
    //TODO: disposing resources after screen is not used

    //TODO: loading snapshots from db
    private final Game game;
    private final DatabaseEngine dbEngine;

    private GameService gs;

    public Coordinator(Game game, DatabaseEngine dbEngine) {
        this.game = game;
        this.dbEngine = dbEngine;
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
        SnapshotService sns = new SnapshotService(dbEngine);
        GameSnapshot gameSnapshot = sns.getLastSnapshot();

        //TurnService ts = new TurnService();
        gs = new GameService();
        GamePresenter gamePresenter = new GamePresenter();
        gs.init(sns, gameSnapshot);
        gamePresenter.init(gs, this);
        game.setScreen(new GameScreen(gamePresenter));
    }

    public void quit() {
        if (gs != null) {
            gs.abort();
            gs = null;
        }

        Gdx.app.exit();
    }
}
