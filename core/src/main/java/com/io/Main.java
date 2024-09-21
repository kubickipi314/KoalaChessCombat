package com.io;

import com.badlogic.gdx.Game;
import com.io.db.DatabaseEngine;
import com.io.service.LevelService;
import com.io.service.SnapshotService;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private DatabaseEngine dbEngine;
    private Coordinator coordinator;

    @Override
    public void create() {
        dbEngine = new DatabaseEngine("jdbc:sqlite:game.db");
//        dbEngine.clear();

        var ls = new LevelService(dbEngine);
        var sns = new SnapshotService(dbEngine);
        coordinator = new Coordinator(this, ls, sns);
        coordinator.setStartScreen();
    }

    @Override
    public void dispose() {
        super.dispose();

        coordinator.quit();
        dbEngine.closeConnection();
    }
}
