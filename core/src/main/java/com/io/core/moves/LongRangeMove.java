package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;

public class LongRangeMove implements Move {
    private final int cost, damage;
    private final int minRange;

    public LongRangeMove(int cost, int damage, int minRange) {
        this.cost = cost;
        this.damage = damage;
        this.minRange = minRange;
    }

    private boolean isInRange(BoardPosition start, BoardPosition end) {
        if (Math.abs(end.x() - start.x()) >= minRange) return true;
        return Math.abs(end.y() - start.y()) >= minRange;
    }


    public boolean isMoveValid(Character character, BoardPosition endPosition, Board board) {
        var startPosition = character.getPosition();
        return isInRange(startPosition, endPosition);
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position, Board board) {
        ArrayList<BoardPosition> result = new ArrayList<>();
        for (int i = 0; i <= board.boardWidth; i++) {
            for (int j = 0; j < board.boardHeight; j++) {
                BoardPosition currentPosition = new BoardPosition(i, j);
                if (isInRange(position, currentPosition) && board.isValidCell(currentPosition) && !board.getCell(currentPosition).isBlocked) {
                    result.add(currentPosition);
                }
            }
        }
        return result;
    }


    @Override
    public MoveType getType() {
        return null;//move doesn't have a texture
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
