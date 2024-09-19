package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;

import static com.io.core.moves.MoveType.KNIGHT;

public class KnightMove implements Move {

    private final int cost, damage;

    private static final int maxReach = 1;
    private static final int[] DX = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int[] DY = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final MoveType type = KNIGHT;

    public KnightMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition, Board board) {
        var startPosition = character.getPosition();
        var attackedCharacter = board.getCharacter(endPosition);
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam()) return false;

        if (startPosition.x() == endPosition.x() || startPosition.y() == endPosition.y()) return false;
        int dx = Integer.signum(endPosition.x() - startPosition.x());
        int dy = Integer.signum(endPosition.y() - startPosition.y());

        return MovesUtils.isValidRayMove(dx, 2 * dy, maxReach, startPosition, endPosition, board) ||
                MovesUtils.isValidRayMove(2 * dx, dy, maxReach, startPosition, endPosition, board);
    }

    @Override
    public List<BoardPosition> getAccessibleCells(Character character, Board board) {
        var position = character.getPosition();
        var accessibleCells = new ArrayList<BoardPosition>();

        for (int i = 0; i < DX.length; i++) {
            accessibleCells.addAll(MovesUtils.getRayAccessibleCells(DX[i], DY[i], maxReach, board, position));
        }

        return MovesUtils.sanitizeAccessibleCells(accessibleCells, character, board);
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
