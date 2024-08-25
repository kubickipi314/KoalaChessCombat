package com.io.service;

import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.character.Player;
import com.io.core.moves.KingMove;
import com.io.core.moves.Move;
import com.io.viewmodel.GameViewModel;

import java.util.ArrayList;

public class GameService {
    final static int DEFAULT_ROOM_WIDTH = 10;
    final static int DEFAULT_ROOM_HEIGHT = 10;

    private GameViewModel gvm;
    private TurnService ts;

    private int roomWidth = DEFAULT_ROOM_WIDTH;
    private int roomHeight = DEFAULT_ROOM_HEIGHT;
    private Player player;

    public GameService() {
    }

    public void initialize(GameViewModel gvm, TurnService ts) {
        this.gvm = gvm;
        this.ts = ts;

        BoardPosition playerStartingPosition = new BoardPosition(1, 0);
        var moves = new ArrayList<Move>();
        moves.add(new KingMove(1, 1));
        this.player = new Player(ts, gvm, playerStartingPosition, moves);
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

    public int getRoomWidth() {
        return roomWidth;
    }

    public int getRoomHeight() {
        return roomHeight;
    }
}
