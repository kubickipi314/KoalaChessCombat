package com.io.service;

import com.io.core.board.Board;
import com.io.core.character.Character;
import com.io.core.moves.MoveDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TurnService {
    public GameService gs;

    private Queue<Character> turnQueue;
    private Board board;

    private boolean firstTurn;

    public void init(GameService gs, List<Character> characters, Board board) {
        this.gs = gs;

        turnQueue = new LinkedList<>(characters);
        this.board = board;

        firstTurn = true;
    }

    public boolean tryMakeMove(MoveDTO moveDTO) {
        if (turnQueue.peek() != moveDTO.character()) {
            System.err.println("TS: Tried to played move on wrong turn!");
            return false;
        }
        return board.tryMakeMove(moveDTO);
    }

    Character nextTurn() {
        assert !turnQueue.isEmpty();
        if (firstTurn) {
            firstTurn = false;
            return turnQueue.peek();
        }

        turnQueue.add(turnQueue.poll());
        var currentCharacter = turnQueue.peek();
        while (currentCharacter.isDead()) {
            turnQueue.poll();
            if (turnQueue.isEmpty()) return null;
            currentCharacter = turnQueue.peek();
        }

        currentCharacter.changeMana(5);
        return currentCharacter;
    }

    void stop() {
        turnQueue = new LinkedList<>();
    }
}
