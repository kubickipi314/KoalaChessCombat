package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;

import java.util.ArrayList;
import java.util.List;

public class KnightMove implements Move {

    private final int cost, damage;

    private static final int maxReach = 1;
    private static final int[] X = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int[] Y = {2, 1, -1, -2, -2, -1, 1, 2};

    public KnightMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board) {
        if (startPosition.x() == endPosition.x() || startPosition.y() == endPosition.y()) return false;
        int dx = Integer.signum(endPosition.x() - startPosition.x());
        int dy = Integer.signum(endPosition.y() - startPosition.y());

        return MovesUtils.isValidRayMove(dx, 2 * dy, maxReach, startPosition, endPosition, board) ||
                MovesUtils.isValidRayMove(2 * dx, dy, maxReach, startPosition, endPosition, board);
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position, Board board) {
        var accessibleCells = new ArrayList<BoardPosition>();

        for (int i = 0; i < X.length; i++) {
            accessibleCells.addAll(MovesUtils.getRayAccessibleCells(X[i], Y[i], maxReach, board, position));
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
