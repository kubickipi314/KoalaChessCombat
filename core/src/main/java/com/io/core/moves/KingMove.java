package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;

import static com.io.core.moves.MoveType.KING;
import static java.lang.Math.abs;

public class KingMove implements Move {
    private final int cost, damage;

    private static final int[] DX = {1, 1, -1, -1, 0, 1, -1, 0};
    private static final int[] DY = {1, -1, 1, -1, 1, 0, 0, -1};
    private static final int maxReach = 1;
    private static final MoveType type = KING;
    private final Board board;

    public KingMove(int cost, int damage, Board board) {
        this.cost = cost;
        this.damage = damage;
        this.board = board;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition) {
        var startPosition = character.getPosition();
        var attackedCharacter = board.getCharacter(endPosition);

        if (!board.isValidCell(endPosition))
            return false;
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam())
            return false;

        if (abs(startPosition.x() - endPosition.x()) > 1 ||
                abs(startPosition.y() - endPosition.y()) > 1)
            return false;

        return true;
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

    @Override
    public boolean moveOnKill() {
        return true;
    }
}
