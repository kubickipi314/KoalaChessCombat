package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;

import java.util.List;

public interface Move {
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board);

    List<BoardPosition> getAccessibleCells(BoardPosition position, Board board);

    int getCost();

    int getDamage();
}
