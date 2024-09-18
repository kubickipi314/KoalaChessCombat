package com.io;

import com.badlogic.gdx.Game;
import com.io.core.snapshot.GameSnapshot;
import com.io.db.DatabaseEngine;
import com.io.presenter.GamePresenter;
import com.io.screens.GameScreen;
import com.io.service.GameService;
import com.io.service.SnapshotService;
import com.io.service.TurnService;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private DatabaseEngine dbEngine;

    private GameService gs;

    @Override
    public void create() {
        dbEngine = new DatabaseEngine("jdbc:sqlite:game.db");
        SnapshotService sns = new SnapshotService(dbEngine);
        GameSnapshot gameSnapshot = sns.getLastSnapshot();

        TurnService ts = new TurnService();
        gs = new GameService();
        GamePresenter gamePresenter = new GamePresenter();
        gs.init(ts, gamePresenter, sns, gameSnapshot);
        gamePresenter.init(gs);
        setScreen(new GameScreen(gamePresenter));

        gs.startGame();
    }

    @Override
    public void dispose() {
        super.dispose();

        gs.abort();
        dbEngine.closeConnection();
    }
}
