package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;

import java.util.ArrayList;
import java.util.List;

public class PawnMove implements Move {

    private final int cost, damage;

    public PawnMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(BoardPosition startPosition, BoardPosition endPosition, Board board) {
        if (!board.isValidCell(startPosition) || !board.isValidCell(endPosition)) return false;
        if (endPosition.y() != 1 + startPosition.y()) return false;
        if (endPosition.x() > startPosition.x() + 1 || endPosition.x() < startPosition.x() - 1) return false;
        var endCell = board.getCell(endPosition);
        if (startPosition.x() == endPosition.x() && endCell.getCharacter() != null) return false;
        if (endCell.getCharacter() == null) return false;
        return true;
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position, Board board) {
        var accessibleCells = new ArrayList<BoardPosition>();
        int x = position.x();
        int y = position.y();
        var frontPosition = new BoardPosition(x, y + 1);
        var frontLeftPosition = new BoardPosition(x - 1, y);
        var frontRightPosition = new BoardPosition(x + 1, y);
        if (board.isValidCell(frontPosition) && board.getCell(frontPosition).getCharacter() == null)
            accessibleCells.add(frontPosition);
        if (board.isValidCell(frontLeftPosition) && board.getCell(frontLeftPosition).getCharacter() != null)
            accessibleCells.add(frontLeftPosition);
        if (board.isValidCell(frontRightPosition) && board.getCell(frontRightPosition).getCharacter() != null)
            accessibleCells.add(frontRightPosition);
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
