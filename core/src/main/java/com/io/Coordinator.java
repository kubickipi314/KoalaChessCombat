package com.io;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.io.core.board.Board;
import com.io.presenter.game.GamePresenter;
import com.io.presenter.menu.MenuPresenter;
import com.io.presenter.menu.StartPresenter;
import com.io.screens.GameScreen;
import com.io.screens.MenuScreen;
import com.io.screens.StartScreen;
import com.io.service.GameService;
import com.io.service.LevelService;
import com.io.service.SnapshotService;

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
        Board board = new Board(sns.getLevelSnapshot(ls.getCurrentLevel()));
        gs = new GameService(sns, ls.getCurrentLevel(), board, board.getCharacters());
        GamePresenter gamePresenter = new GamePresenter(gs, this);
        game.setScreen(new GameScreen(gamePresenter));
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
