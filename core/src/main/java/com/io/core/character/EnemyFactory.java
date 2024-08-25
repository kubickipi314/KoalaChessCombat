package com.io.core.character;

import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.Move;
import com.io.service.TurnService;

import java.util.ArrayList;

public class EnemyFactory {
    TurnService ts;

    public EnemyFactory(TurnService ts) {
        this.ts = ts;
    }

    public Enemy create(BoardPosition position) {
        var moves = new ArrayList<Move>();
        moves.add(new KingMove(1, 1));
        return new SimpleEnemy(ts, position, moves);
    }

    public Enemy[] createMultiple(int count) {
        // Temporary! Probably will just create from some config (e.g. .json file)
        Enemy[] enemies = new Enemy[count];
        for (int i = 0; i < count; ++i)
            enemies[i] = create(new BoardPosition(i, 0));
        return enemies;
    }
}
