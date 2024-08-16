package com.io.core;

public class Board {
    private final Cell[][] board;

    public Board(int width, int height) {
        this.board = new Cell[height][width];
    }

    public boolean tryMakeMove(Move move) {
        // check if move is valid, if no return false

        // update values

        return true;
    }

    public boolean isMoveValid(Move move) {
        // check if out of bounds
        // check if two characters can attack each other

        return true;
    }
}
