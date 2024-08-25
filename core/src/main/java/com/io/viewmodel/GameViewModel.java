package com.io.viewmodel;


import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;
import com.io.core.character.Enemy;
import com.io.core.character.EnemyFactory;
import com.io.core.character.Player;
import com.io.service.GameService;
import com.io.service.TurnService;

import java.util.Arrays;
import java.util.List;

public class GameViewModel {
    private final GameService gs;
    private final TurnService ts;

    private final Player player;

    private int moveIdx = -1;

    public GameViewModel() {
        this.ts = new TurnService();
        this.gs = new GameService();

        gs.initialize(this, ts);
        player = gs.getPlayer();

        EnemyFactory enemyFactory = new EnemyFactory(ts);
        Enemy[] enemies = enemyFactory.createMultiple(10);
        List<Character> characters = Arrays.asList(enemies);
        characters.add(player);

        ts.initialize(gs, characters);
    }

    public void onCellClick(int x, int y) {
        if (moveIdx == -1) {
            System.out.println("Select move type first.");
            return;
        }

        BoardPosition movePosition = new BoardPosition(x, y);
        if (!player.PlayMove(movePosition, moveIdx)) {
            System.out.println("Player failed to make move.");
        }
    }

    public void onMoveTypeClick(int moveIdx) {
        this.moveIdx = moveIdx;
    }

    public void startTurn() {
        // enable UI
    }

    public void onEndTurnClicked() {
        moveIdx = -1;
        ts.endTurn();

        // disable UI
    }

    public void endGame(GameResult gameResult) {
        System.out.println("Game ended, You " + gameResult.toString());
    }
}
