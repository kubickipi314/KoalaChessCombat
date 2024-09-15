package com.io.service;

import com.io.core.board.Board;
import com.io.core.character.Character;
import com.io.core.moves.MoveDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TurnService {
    public GameService gs;

    Queue<Character> turnQueue;
    Board board;

    public void init(GameService gs, List<Character> characters, Board board) {
        this.gs = gs;

        turnQueue = new LinkedList<>(characters);
        this.board = board;
    }

    public boolean tryMakeMove(MoveDTO moveDTO) {
        if (turnQueue.peek() != moveDTO.character()) {
            System.err.println("TS: Tried to played move on wrong turn!");
            return false;
        }

        return board.tryMakeMove(moveDTO);
    }

    public void endTurn() {
        var currentCharacter = turnQueue.peek();
        assert currentCharacter != null;
        currentCharacter.changeMana(1);

        turnQueue.add(turnQueue.poll());
        nextTurn();
    }

    void nextTurn() {
        var currentCharacter = turnQueue.peek();
        assert currentCharacter != null;

        currentCharacter.startTurn();
    }
}
