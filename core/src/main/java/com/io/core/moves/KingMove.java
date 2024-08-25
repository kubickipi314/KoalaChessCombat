package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.board.Cell;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class KingMove implements Move {
    private final int cost, damage;

    public KingMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board) {
        Cell cell = board.getCell(endPosition);
        if (cell.isBlocked) return false;

        if (abs(startPosition.x() - endPosition.x()) <= 1 ||
            abs(startPosition.y() - endPosition.y()) <= 1)
            return false;

        return true;
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position, Board board) {
        var accessibleList = new ArrayList<BoardPosition>();
        for (int x = position.x() - 1; x <= position.x() + 1; x++) {
            for (int y = position.y() - 1; x <= position.y() + 1; y++) {
                var movePosition = new BoardPosition(x, y);
                if (isMoveValid(position, movePosition, board)) {
                    accessibleList.add(new BoardPosition(x, y));
                }
            }
        }
        return accessibleList;
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
