package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.board.Cell;

import java.util.ArrayList;
import java.util.List;

import static com.io.core.moves.MoveType.KING;
import static java.lang.Math.abs;

public class KingMove implements Move {
    private final int cost, damage;

    private static final int[] X = {1, 1, -1, -1, 0, 1, -1, 0};
    private static final int[] Y = {1, -1, 1, -1, 1, 0, 0, -1};
    private static final int maxReach = 1;
    private static final MoveType type = KING;

    public KingMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board) {
        Cell cell = board.getCell(endPosition);
        if (cell.isBlocked) return false;

        if (abs(startPosition.x() - endPosition.x()) > 1 ||
                abs(startPosition.y() - endPosition.y()) > 1)
            return false;

        return true;
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
