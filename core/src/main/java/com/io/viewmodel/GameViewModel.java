package com.io.viewmodel;


import com.io.core.Character;
import com.io.core.*;
import com.io.service.GameService;
import com.io.service.TurnService;

import java.util.Arrays;
import java.util.List;

public class GameViewModel {
    final GameService gs;
    final TurnService ts;

    MoveType moveType;

    public GameViewModel() {
        this.ts = new TurnService();
        this.gs = new GameService();

        gs.initialize(this, ts);

        EnemyFactory enemyFactory = new EnemyFactory(ts);
        Enemy[] enemies = enemyFactory.createMultiple(10);
        List<Character> characters = Arrays.asList(enemies);
        characters.add(gs.getPlayer());

        ts.initialize(gs, characters);
    }

    public void onCellClick(int x, int y) {
        if (moveType == null) {
            System.out.println("Select move type.");
            return;
        }

        BoardPosition position = new BoardPosition(x, y);
        if (!gs.getPlayer().PlayMove(position, moveType)) {
            System.out.println("Player failed to make move.");
        }
    }

    public void onMoveTypeClick(int moveTypeIdx) {
        moveType = gs.getPlayer().getMoveType(moveTypeIdx);
    }

    public void startTurn() {
        // enable UI
    }

    public void onEndTurnClicked() {
        ts.endTurn();

        // disable UI
    }

    public void endGame(GameResult gameResult) {
        System.out.println("Game ended, You " + gameResult.toString());
    }
}
