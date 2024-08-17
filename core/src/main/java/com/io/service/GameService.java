package com.io.service;

import com.io.core.BoardPosition;
import com.io.core.GameResult;
import com.io.core.Player;
import com.io.viewmodel.GameViewModel;

public class GameService {
    final static int DEFAULT_ROOM_WIDTH = 10;
    final static int DEFAULT_ROOM_HEIGHT = 10;

    GameViewModel gvm;
    TurnService ts;

    int roomWidth = DEFAULT_ROOM_WIDTH;
    int roomHeight = DEFAULT_ROOM_HEIGHT;
    Player player;

    public GameService() {
    }

    public void initialize(GameViewModel gvm, TurnService ts) {
        this.gvm = gvm;
        this.ts = ts;

        BoardPosition playerStartingPosition = new BoardPosition(1 ,0);
        this.player = new Player(ts, gvm, playerStartingPosition);
    }

    public void initialize(GameViewModel gvm, TurnService ts, int roomWidth, int roomHeight) {
        initialize(gvm, ts);
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
    }

    public void endGame(GameResult gameResult) {
        // stop game
        gvm.endGame(gameResult);
    }

    public Player getPlayer() {
        return player;
    }
}
