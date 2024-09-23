package com.io.core.moves;

import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.List;

public interface Move {
    boolean isMoveValid(Character character, BoardPosition endPosition);

    List<BoardPosition> getAccessibleCells(BoardPosition position);

    MoveType getType();

    int getCost();

    int getDamage();

     boolean moveOnKill();
}
