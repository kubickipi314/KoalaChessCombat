package com.io.core;

public class Board {
    final Cell[][] board;
    final int boardWidth, boardHeight;

    public Board(int width, int height) {
        this.board = new Cell[height][width];
        boardWidth = width;
        boardHeight = height;
    }

    public boolean tryMakeMove(Move move) {
        // check if move is valid, if no return false
        Character currentCharacter = move.character();
        int moveX = move.position().x(), moveY = move.position().y();
        Cell destinationCell = board[moveY][moveX];
        Character attackedCharacter = destinationCell.getCharacter();

        if (!isMoveValid(moveX, moveY, destinationCell, currentCharacter)) return false;

        if (attackedCharacter != null) {
            attackedCharacter.increaseHealth(-move.moveType().damage());
            if (attackedCharacter.currentHealth < 0) {
                destinationCell.setCharacter(currentCharacter);
            }
        } else {
            destinationCell.setCharacter(currentCharacter);
        }

        currentCharacter.increaseMana(-move.moveType().cost());
        // move current character based on move type?

        return true;
    }

    public boolean isMoveValid(int moveX, int moveY, Cell destinationCell, Character currentCharacter) {
        // check if out of bounds
        // check if two characters can attack each other

        if (moveX > boardWidth || moveY > boardHeight)
            return false;
        if (destinationCell.isBlocked())
            return false;

        Character attackedCharacter = destinationCell.getCharacter();
        if (attackedCharacter != null && currentCharacter.team == attackedCharacter.team)
            return false;

        return true;
    }
}
