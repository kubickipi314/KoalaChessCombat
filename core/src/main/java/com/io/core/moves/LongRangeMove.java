package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LongRangeMove implements Move {
    private final int cost, damage;
    private final int minRange;
    private final Board board;

    public LongRangeMove(int cost, int damage, int minRange, Board board) {
        this.cost = cost;
        this.damage = damage;
        this.minRange = minRange;
        this.board = board;
    }

    private boolean isInRange(BoardPosition start, BoardPosition end) {
        if (Math.abs(end.x() - start.x()) >= minRange) return true;
        return Math.abs(end.y() - start.y()) >= minRange;

    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition) {
        var startPosition = character.getPosition();
        if (!board.isValidCell(endPosition)) return false;
        var attackedCharacter = board.getCharacter(endPosition);
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam()) return false;
        return isInRange(startPosition, endPosition);
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position) {
        var character = board.getCharacter(position);
        ArrayList<BoardPosition> accessibleCells = new ArrayList<>();
        for (int i = 0; i <= board.getBoardWidth(); i++) {
            for (int j = 0; j < board.getBoardHeight(); j++) {
                BoardPosition currentPosition = new BoardPosition(i, j);
                if (Objects.equals(character.getPosition(), currentPosition)) continue;
                if (isInRange(position, currentPosition) && board.isValidCell(currentPosition)) {
                    accessibleCells.add(currentPosition);
                }
            }
        }

        return MovesUtils.sanitizeAccessibleCells(accessibleCells, character, board);
    }


    @Override
    public MoveType getType() {
        return MoveType.LONG_RANGE;
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
