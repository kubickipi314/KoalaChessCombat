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

    public KingMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition, Board board) {
        var startPosition = character.getPosition();
        var cell = board.getCell(endPosition);

        if (cell.isBlocked)
            return false;
        if (cell.getCharacter() != null && cell.getCharacter().getTeam() == character.getTeam())
            return false;

        if (abs(startPosition.x() - endPosition.x()) > 1 ||
                abs(startPosition.y() - endPosition.y()) > 1)
            return false;

        return true;
    }

    @Override
    public List<BoardPosition> getAccessibleCells(Character character, Board board) {
        var position = character.getPosition();
        var accessibleCells = new ArrayList<BoardPosition>();

        for (int i = 0; i < DX.length; i++) {
            accessibleCells.addAll(MovesUtils.getRayAccessibleCells(DX[i], DY[i], maxReach, board, position));
        }

        return accessibleCells.stream()
                .filter(currentPosition -> {
                    var attackedCharacter = board.getCell(currentPosition).getCharacter();
                    if (attackedCharacter == null) {
                        return true;
                    }
                    return attackedCharacter.getTeam() != character.getTeam();
                })
                .toList();
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
