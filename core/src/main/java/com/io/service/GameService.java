package com.io.service;

import com.io.core.GameResult;
import com.io.core.Player;

public class GameService {
    final static int DEFAULT_ROOM_WIDTH = 10;
    final static int DEFAULT_ROOM_HEIGHT = 10;

    TurnService ts;

    int roomWidth = DEFAULT_ROOM_WIDTH;
    int roomHeight = DEFAULT_ROOM_HEIGHT;
    Player player;

    public GameService() {
    }

    public void initialize(TurnService ts) {
        this.ts = ts;
        this.player = new Player(ts);
    }

    public void initialize(TurnService ts, int roomWidth, int roomHeight) {
        initialize(ts);
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
    }

    public void endGame(GameResult gameResult) {
        // stop gae
    }

    public Player getPlayer() {
        return player;
    }
}
