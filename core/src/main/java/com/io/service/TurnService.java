package com.io.service;

import com.io.core.Board;
import com.io.core.Character;
import com.io.core.GameResult;
import com.io.core.Move;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class TurnService {
    public GameService gs;

    int currentTour;
    Queue<Character> tourQueue;

    Board board;

    public TurnService() {
    }

    public void initialize(GameService gs, Collection<Character> characters) {
        this.gs = gs;

        currentTour = 0;
        tourQueue = new LinkedList<>(characters);

        board = new Board(gs.roomWidth, gs.roomHeight);
    }

    public boolean tryMakeMove(Move move) {
        // check if given move is valid
        if (!board.tryMakeMove(move)) return false;

        // check win/lose conditions, if either end game
        GameResult gameResult = checkEndGameCondition();
        if (gameResult != GameResult.NONE)
            gs.endGame(gameResult);

        return true;
    }

    GameResult checkEndGameCondition() {
        // check win/lose conditions

        return GameResult.NONE;
    }

    public Board getBoard() {
        return board;
    }
}
