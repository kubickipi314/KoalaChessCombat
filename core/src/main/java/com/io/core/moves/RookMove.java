package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;

import java.util.ArrayList;
import java.util.List;

public class RookMove implements Move {

    private final int cost, damage;

    RookMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board) {
        if (startPosition.x() != endPosition.x() && startPosition.y() != endPosition.y()) {
            return false;
        }
        int min, max, c;
        boolean isXEqual = startPosition.x() == endPosition.x();
        if (isXEqual) {
            min = Math.min(startPosition.y(), endPosition.y());
            max = Math.max(startPosition.y(), endPosition.y());
            c = startPosition.x();
        } else {
            min = Math.min(startPosition.x(), endPosition.x());
            max = Math.max(startPosition.x(), endPosition.x());
            c = startPosition.x();
        }
        for (int i = min; i < max; i++) {
            if (board.getCell(isXEqual ? new BoardPosition(c, i) : new BoardPosition(i, c)).isBlocked) return false;
        }

        return true;
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position, Board board) {

        var accessibleCells = new ArrayList<BoardPosition>();

        for (int i = 0; i < board.boardHeight; i++) {
            if (i == position.y()) continue;
            accessibleCells.add(new BoardPosition(position.x(), i));
        }
        for (int i = 0; i < board.boardWidth; i++) {
            if (i == position.x()) continue;
            accessibleCells.add(new BoardPosition(i, position.y()));
        }

        return accessibleCells;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
