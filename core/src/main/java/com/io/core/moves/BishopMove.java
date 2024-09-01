package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;

import java.util.ArrayList;
import java.util.List;

public class BishopMove implements Move {

    private final int cost, damage;
    private static final int[] X = {1, 1, -1, -1};
    private static final int[] Y = {1, -1, 1, -1};

    public BishopMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board) {
        if (startPosition.x() == endPosition.x() || startPosition.y() == endPosition.y()) return false;
        int x = endPosition.x() > startPosition.x() ? 1 : -1;
        int y = endPosition.y() > startPosition.y() ? 1 : -1;
        return MovesUtils.isValidRayMove(x, y, Integer.MAX_VALUE, startPosition, endPosition, board);
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position, Board board) {
        var accessibleCells = new ArrayList<BoardPosition>();

        for (int i = 0; i < X.length; i++) {
            accessibleCells.addAll(MovesUtils.getRayAccessibleCells(X[i], Y[i], Integer.MAX_VALUE, board, position));
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
