package com.io.service;

import com.io.core.board.Board;
import com.io.core.character.Character;
import com.io.core.moves.MoveDTO;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TurnService {
    public GameService gs;

    private Deque<Character> turnQueue;
    private Board board;

    public void init(GameService gs, List<Character> characters, Board board) {
        this.gs = gs;

        turnQueue = new LinkedList<>(characters);
        this.board = board;
    }

    public boolean tryMakeMove(MoveDTO moveDTO) {
        if (turnQueue.getLast() != moveDTO.character()) {
            System.err.println("TS: Tried to played move on wrong turn!");
            return false;
        }
        return board.tryMakeMove(moveDTO);
    }

    Character nextTurn() {
        assert !turnQueue.isEmpty();

        var currentCharacter = turnQueue.pollFirst();
        while (currentCharacter.isDead()) {
            currentCharacter = turnQueue.pollFirst();
            if (turnQueue.isEmpty()) return null;
        }
        turnQueue.addLast(currentCharacter);

        currentCharacter.changeMana(5);
        return currentCharacter;
    }

    void stop() {
        turnQueue = new LinkedList<>();
    }
}
