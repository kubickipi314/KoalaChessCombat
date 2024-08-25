package com.io.core.board;

import com.io.core.moves.MoveDTO;

public class Board {
    private final Cell[][] board;
    public final int boardWidth, boardHeight;

    public Board(int width, int height) {
        this.board = new Cell[height][width];
        boardWidth = width;
        boardHeight = height;
    }

    public boolean tryMakeMove(MoveDTO moveDTO) {
        var move = moveDTO.move();
        var movePosition = moveDTO.boardPosition();
        var character = moveDTO.character();

        // check if move is valid, if no return false
        var destinationCell = getCell(movePosition);
        var attackedCharacter = destinationCell.getCharacter();

        if (character.getCurrentMana() < move.getCost()) return false;
        if (attackedCharacter != null && character.getTeam() == attackedCharacter.getTeam()) return false;
        if (!move.isMoveValid(character.getPosition(), movePosition, this)) return false;

        character.changeMana(-move.getCost());
        if (attackedCharacter != null) {
            attackedCharacter.changeHealth(-move.getDamage());
        }
        if (attackedCharacter == null || attackedCharacter.getCurrentHealth() < 0) {
            destinationCell.setCharacter(character);
            character.setPosition(movePosition);
        }

        return true;
    }

    public Cell getCell(BoardPosition position) {
        return board[position.y()][position.x()];
    }
}
