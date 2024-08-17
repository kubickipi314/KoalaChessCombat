package com.io.core;

import com.io.service.TurnService;

public class EnemyFactory {
    TurnService ts;

    public EnemyFactory(TurnService ts) {
        this.ts = ts;
    }

    public Enemy create(BoardPosition position) {
        return new SimpleEnemy(ts, position);
    }

    public Enemy[] createMultiple(int count) {
        // Temporary! Probably will just create from some config (e.g. .json file)
        Enemy[] enemies = new Enemy[count];
        for (int i = 0; i < count; ++i)
            enemies[i] = create(new BoardPosition(i, 0));
        return enemies;
    }
}
