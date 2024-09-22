package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.List;

public interface Move {
    boolean isMoveValid(Character character, BoardPosition endPosition, Board board);

    List<BoardPosition> getAccessibleCells(Character character, Board board);

    MoveType getType();

    int getCost();

    int getDamage();

     boolean moveOnKill();
}
