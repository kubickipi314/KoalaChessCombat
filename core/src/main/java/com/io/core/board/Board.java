package com.io.core.board;

import com.io.core.moves.MoveDTO;

public class Board {
    private final Cell[][] board;
    public final int boardWidth, boardHeight;

    public Board(int width, int height) {
        this.board = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell(false);
            }
        }
        boardWidth = width;
        boardHeight = height;
    }

    public boolean tryMakeMove(MoveDTO moveDTO) {
        var move = moveDTO.move();
        var movePosition = moveDTO.boardPosition();
        var character = moveDTO.character();
        // check if move is valid, if no return false
        var destinationCell = getCell(movePosition);
        var startCell = getCell(character.getPosition());

        var attackedCharacter = destinationCell.getCharacter();
        System.out.print("try move " + move + " " + character + " attacks " + attackedCharacter + "\n");
        System.out.println("from " + character.getPosition() + " to " + movePosition);

        if (character.getCurrentMana() < move.getCost()) return false;
        if (attackedCharacter != null && character.getTeam() == attackedCharacter.getTeam()) return false;
        if (!move.isMoveValid(character.getPosition(), movePosition, this)) return false;

        System.out.println("valid move\n");
        character.changeMana(-move.getCost());
        if (attackedCharacter != null) {
            attackedCharacter.changeHealth(-move.getDamage());
        }
        if (attackedCharacter == null || attackedCharacter.getCurrentHealth() < 0) {
            destinationCell.setCharacter(character);
            character.setPosition(movePosition);
            startCell.setCharacter(null);
        }

        return true;
    }

    //works only for rectangular board can be changed in the future
    public boolean isValidCell(BoardPosition position) {
        if (position.x() < 0 || position.x() >= this.boardWidth) return false;
        if (position.y() < 0 || position.y() >= this.boardHeight) return false;
        if (getCell(position).isBlocked) return false;
        return true;
    }

    public Cell getCell(BoardPosition position) {
        return board[position.y()][position.x()];
    }
}
