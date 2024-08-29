package com.io.service;

import com.io.core.GameResult;
import com.io.core.board.Board;
import com.io.core.character.Character;
import com.io.core.moves.MoveDTO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class TurnService {
    public GameService gs;

    Queue<Character> turnQueue;

    Board board;

    public TurnService() {
    }

    public void initialize(GameService gs, Collection<Character> characters) {
        this.gs = gs;

        turnQueue = new LinkedList<>(characters);
        board = new Board(gs.getRoomWidth(), gs.getRoomHeight());
    }

    public boolean tryMakeMove(MoveDTO moveDTO) {
        // check if given move is valid
        if (turnQueue.peek() != moveDTO.character()) {
            System.out.println("TS: Tried to played move on wrong turn!");
            return false;
        }

        if (!board.tryMakeMove(moveDTO)) return false;

        GameResult gameResult = checkEndGameCondition();
        if (gameResult != GameResult.NONE)
            gs.endGame(gameResult);

        return true;
    }

    GameResult checkEndGameCondition() {
        // check win/lose conditions

        return GameResult.NONE;
    }

    public void endTurn() {
        // handle on end turn events:
        // replenish mana etc.

        nextTurn();
    }

    void nextTurn() {
        Character currentCharacter = turnQueue.poll();
        turnQueue.add(currentCharacter);

        assert currentCharacter != null;
        currentCharacter.startTurn();
    }


    public Board getBoard() {
        return board;
    }
}
