package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;

import static com.io.core.moves.MoveType.QUEEN;

public class QueenMove implements Move {

    private final int cost, damage;
    private static final int[] DX = {1, 1, -1, -1, 0, 1, -1, 0};
    private static final int[] DY = {1, -1, 1, -1, 1, 0, 0, -1};
    private static final int maxReach = Integer.MAX_VALUE;
    private static final MoveType type = QUEEN;
    private final Board board;

    public QueenMove(int cost, int damage, Board board) {
        this.cost = cost;
        this.damage = damage;
        this.board = board;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition) {
        var startPosition = character.getPosition();

        if (startPosition == endPosition) return false;
        var attackedCharacter = board.getCharacter(endPosition);
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam()) return false;

        int dx = endPosition.x() - startPosition.x();
        int dy = endPosition.y() - startPosition.y();

        return MovesUtils.isValidRayMove(Integer.signum(dx),
                Integer.signum(dy), maxReach, startPosition, endPosition, board);
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position) {
        var character = board.getCharacter(position);
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
