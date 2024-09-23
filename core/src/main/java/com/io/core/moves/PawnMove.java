package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

import java.util.ArrayList;
import java.util.List;

public class PawnMove implements Move {

    private final int cost, damage;
    private final Board board;

    public PawnMove(int cost, int damage, Board board) {
        this.cost = cost;
        this.damage = damage;
        this.board = board;
    }

    @Override
    public boolean isMoveValid(Character character, BoardPosition endPosition) {
        var startPosition = character.getPosition();

        if (!board.isValidCell(startPosition) || !board.isValidCell(endPosition)) return false;
        var attackedCharacter = board.getCharacter(endPosition);
        if (attackedCharacter != null && attackedCharacter.getTeam() == character.getTeam()) return false;
        if (endPosition.y() != 1 + startPosition.y()) return false;
        if (endPosition.x() > startPosition.x() + 1 || endPosition.x() < startPosition.x() - 1) return false;
        if (startPosition.x() == endPosition.x() && attackedCharacter != null) return false;
        if (attackedCharacter == null) return false;
        return true;
    }

    @Override
    public List<BoardPosition> getAccessibleCells(BoardPosition position) {
        var character = board.getCharacter(position);
        var accessibleCells = new ArrayList<BoardPosition>();
        int x = position.x();
        int y = position.y();
        var frontPosition = new BoardPosition(x, y + 1);
        var frontLeftPosition = new BoardPosition(x - 1, y + 1);
        var frontRightPosition = new BoardPosition(x + 1, y + 1);
        if (board.isValidCell(frontPosition) && board.getCharacter(frontPosition) == null)
            accessibleCells.add(frontPosition);
        var frontLeftCharacter = board.getCharacter(frontLeftPosition);
        if (board.isValidCell(frontLeftPosition) && frontLeftCharacter != null
                && frontLeftCharacter.getTeam() != character.getTeam())
            accessibleCells.add(frontLeftPosition);

        var frontRightCharacter = board.getCharacter(frontRightPosition);
        if (board.isValidCell(frontRightPosition) && frontRightCharacter != null
                && frontRightCharacter.getTeam() != character.getTeam())
            accessibleCells.add(frontRightPosition);
        return accessibleCells;
    }

    @Override
    public MoveType getType() {
        return MoveType.PAWN;
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
