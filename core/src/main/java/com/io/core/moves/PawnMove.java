package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;

public class PawnMove implements Move {

    private final int cost, damage;

    public PawnMove(int cost, int damage) {
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition, Board board) {
        var startPosition = character.getPosition();

        if (!board.isValidCell(startPosition) || !board.isValidCell(endPosition)) return false;
        var attackedCharacter = board.getCell(endPosition).getCharacter();
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam()) return false;
        if (endPosition.y() != 1 + startPosition.y()) return false;
        if (endPosition.x() > startPosition.x() + 1 || endPosition.x() < startPosition.x() - 1) return false;
        var endCell = board.getCell(endPosition);
        if (startPosition.x() == endPosition.x() && endCell.getCharacter() != null) return false;
        if (endCell.getCharacter() == null) return false;
        return true;
    }

    @Override
    public List<BoardPosition> getAccessibleCells(Character character, Board board) {
        var position = character.getPosition();
        var accessibleCells = new ArrayList<BoardPosition>();
        int x = position.x();
        int y = position.y();
        var frontPosition = new BoardPosition(x, y + 1);
        var frontLeftPosition = new BoardPosition(x - 1, y + 1);
        var frontRightPosition = new BoardPosition(x + 1, y + 1);
        if (board.isValidCell(frontPosition) && board.getCell(frontPosition).getCharacter() == null)
            accessibleCells.add(frontPosition);
        var frontLeftCharacter = board.getCell(frontLeftPosition).getCharacter();
        if (board.isValidCell(frontLeftPosition) && frontLeftCharacter != null
                && frontLeftCharacter.getTeam() != character.getTeam())
            accessibleCells.add(frontLeftPosition);

        var frontRightCharacter = board.getCell(frontRightPosition).getCharacter();
        if (board.isValidCell(frontRightPosition) && frontRightCharacter != null
                && frontRightCharacter.getTeam() != character.getTeam())
            accessibleCells.add(frontRightPosition);
        return accessibleCells;
    }


    //currently unsupported
    @Override
    public MoveType getType() {
        return null;
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
