package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShortRangeMove implements Move {
    private final int cost, damage;
    private final int maxRange;

    public ShortRangeMove(int cost, int damage, int maxRange) {
        this.cost = cost;
        this.damage = damage;
        this.maxRange = maxRange;
    }

    private boolean isInRange(BoardPosition start, BoardPosition end) {
        return Math.abs(end.x() - start.x()) <= maxRange && Math.abs(end.y() - start.y()) <= maxRange;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition, Board board) {
        var startPosition = character.getPosition();
        if (!board.isValidCell(endPosition)) return false;
        var attackedCharacter = board.getCharacter(endPosition);
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam()) return false;
        return isInRange(startPosition, endPosition);
    }

    @Override
    public List<BoardPosition> getAccessibleCells(Character character, Board board) {
        var position = character.getPosition();
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
        return MoveType.SHORT_RANGE;
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
