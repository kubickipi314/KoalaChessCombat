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
    private GamePresenter gamePresenter;
    private final TurnService ts;
    private int roomWidth = CONST.DEFAULT_ROOM_WIDTH;
    private int roomHeight = CONST.DEFAULT_ROOM_WIDTH;
    private final Player player;
    private int chosenMove = 0;

    public GameService(TurnService ts) {
        this.ts = ts;
        BoardPosition playerStartingPosition = new BoardPosition(1, 0);
        var moves = new ArrayList<Move>();
        moves.add(new KingMove(1, 1));
        moves.add(new KnightMove(2, 2));
        moves.add(new RookMove(3, 4));
        moves.add(new BishopMove(2, 1));
        moves.add(new QueenMove(5, 5));
        this.player = new Player(ts, gamePresenter, playerStartingPosition, moves);
        ts.initialize(this, Collections.singletonList(this.player));
    }

    public void endGame(GameResult gameResult) {
        gamePresenter.endGame(gameResult);
    }

    public void movePlayer(BoardPosition boardPosition) {
        player.PlayMove(boardPosition, chosenMove);
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
