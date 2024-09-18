package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;

import static com.io.core.moves.MoveType.BISHOP;

public class BishopMove implements Move {

    private final int cost, damage;
    private static final int[] DX = {1, 1, -1, -1};
    private static final int[] DY = {1, -1, 1, -1};
    private static final int maxReach = Integer.MAX_VALUE;
    private static final MoveType type = BISHOP;

    public BishopMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition, Board board) {
        var startPosition = character.getPosition();

        if (startPosition.x() == endPosition.x() || startPosition.y() == endPosition.y()) return false;
        var attackedCharacter = board.getCell(endPosition).getCharacter();
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam()) return false;
        int x = endPosition.x() > startPosition.x() ? 1 : -1;
        int y = endPosition.y() > startPosition.y() ? 1 : -1;
        return MovesUtils.isValidRayMove(x, y, maxReach, startPosition, endPosition, board);
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
