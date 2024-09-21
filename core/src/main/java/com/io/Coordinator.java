package com.io;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.io.db.DatabaseEngine;
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

import java.util.List;

public class Coordinator {
    //TODO: disposing resources after screen is not used

    //TODO: loading snapshots from db
    private final Game game;
    private final DatabaseEngine dbEngine;

    private GameService gs;

    private List<Long> levels;
    private int levelIdx;

    public Coordinator(Game game, DatabaseEngine dbEngine) {
        this.game = game;
        this.dbEngine = dbEngine;

        levels = List.of();
        levelIdx = 0;
    }

    public void setStartScreen() {
        StartPresenter startPresenter = new StartPresenter(this);
        StartScreen startScreen = new StartScreen(startPresenter);
        game.setScreen(startScreen);
    }

    public void setMenuScreen() {
        LevelService ls = new LevelService(dbEngine);
        levels = ls.getLevels();

        MenuPresenter menuPresenter = new MenuPresenter(this);
        MenuScreen menuScreen = new MenuScreen(menuPresenter);
        game.setScreen(menuScreen);
    }

    public void setGameScreen(long levelId) {
        SnapshotService sns = new SnapshotService(dbEngine);
        TurnService ts = new TurnService();
        gs = new GameService();
        GamePresenter gamePresenter = new GamePresenter();

        gs.init(ts, gamePresenter, sns, levelId);
        gamePresenter.init(gs, this);
        game.setScreen(new GameScreen(gamePresenter));

        gs.startGame();
    }

    public void quit() {
        if (gs != null) gs.abort();
        Gdx.app.exit();
    }

    public void startLevel() {
        setGameScreen(levels.get(levelIdx));
    }

    public void previousLevel() {
        levelIdx = (levelIdx + levels.size() - 1) % levels.size();
    }

    public void nextLevel() {
        levelIdx = (levelIdx + 1) % levels.size();
    }
}
