package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;

import java.util.ArrayList;
import java.util.List;

import static com.io.core.moves.MoveType.BISHOP;

public class BishopMove implements Move {

    private final int cost, damage;
    private static final int[] X = {1, 1, -1, -1};
    private static final int[] Y = {1, -1, 1, -1};
    private static final int maxReach = Integer.MAX_VALUE;
    private static final MoveType type = BISHOP;

    public BishopMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board) {
        if (startPosition.x() == endPosition.x() || startPosition.y() == endPosition.y()) return false;
        int x = endPosition.x() > startPosition.x() ? 1 : -1;
        int y = endPosition.y() > startPosition.y() ? 1 : -1;
        return MovesUtils.isValidRayMove(x, y, maxReach, startPosition, endPosition, board);
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
    public MoveType getType() {
        return type;
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
