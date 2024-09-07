package com.io.service;

import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Player;
import com.io.core.moves.*;
import com.io.presenter.GamePresenter;

import java.util.ArrayList;
import java.util.Collections;

public class GameService {

    private GamePresenter gvm;
    private final TurnService ts;

    private int roomWidth = CONST.DEFAULT_ROOM_WIDTH;
    private int roomHeight = CONST.DEFAULT_ROOM_HEIGHT;
    private Player player;
    private int chosenMove = 0;

    public GameService() {
        ts = new TurnService();
        BoardPosition playerStartingPosition = new BoardPosition(1, 0);
        var moves = new ArrayList<Move>();
        moves.add(new KingMove(1, 1));
        moves.add(new KnightMove(1, 1));
        moves.add(new RookMove(1, 1));
        moves.add(new BishopMove(1, 1));
        moves.add(new QueenMove(1, 1));
        this.player = new Player(ts, gvm, playerStartingPosition, moves);
        ts.initialize(this, Collections.singletonList(this.player));
    }

    public void endGame(GameResult gameResult) {
        // stop game
        gvm.endGame(gameResult);
    }

    public boolean movePlayer(BoardPosition boardPosition) {
        return player.PlayMove(boardPosition, chosenMove);
    }

    public void setMove(int chosenMove) {
        this.chosenMove = chosenMove;
    }

    public int getChosenMove() {
        return chosenMove;
    }

    public void increaseMana(int mana) {
        player.changeMana(mana);
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

    public Board getBoard() {
        return ts.getBoard();
    }
}
