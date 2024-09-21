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
import com.io.service.LevelService;
import com.io.service.SnapshotService;
import com.io.service.TurnService;

public class Coordinator {
    //TODO: disposing resources after screen is not used

    //TODO: loading snapshots from db
    private final Game game;
    private final LevelService ls;
    private final SnapshotService sns;

    private GameService gs;

    public Coordinator(Game game, LevelService ls, SnapshotService sns) {
        this.game = game;
        this.ls = ls;
        this.sns = sns;
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
        gs = new GameService();
        GamePresenter gamePresenter = new GamePresenter();

        gs.init(ts, gamePresenter, sns, ls.getCurrentLevel());
        gamePresenter.init(gs, this);
        game.setScreen(new GameScreen(gamePresenter));

        gs.startGame();
    }

    public void quit() {
        if (gs != null) gs.abort();
        Gdx.app.exit();
    }

    public long getCurrentLevel() {
        return ls.getCurrentLevel();
    }

    public void previousLevel() {
        ls.previousLevel();
    }

    public void nextLevel() {
        ls.nextLevel();
    }
}
