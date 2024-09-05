package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;

import java.util.ArrayList;

public final class MovesUtils {
    private MovesUtils() {
        throw new UnsupportedOperationException();
    }


    private static boolean onRay(int x, int y, BoardPosition startPosition, BoardPosition endPosition) {
        int dx = endPosition.x() - startPosition.x();
        int dy = endPosition.y() - startPosition.y();
        if (dx * y != dy * x) return false;
        if (x * dx < 0) return false;
        if (y * dy < 0) return false;
        return true;
    }

    public static boolean isValidRayMove(int x, int y, int maxReach, BoardPosition startPosition, BoardPosition endPosition, Board board) {
        if (startPosition == endPosition) return false;
        if (!onRay(x, y, startPosition, endPosition)) return false;

        int currentX = startPosition.x();
        int currentY = startPosition.x();
        int count = 0;
        while (count < maxReach) {
            count++;
            currentX += x;
            currentY += y;
            var currentPosition = new BoardPosition(currentX, currentY);
            if (!board.isValidCell(currentPosition)) return false;
            if (board.getCell(currentPosition).getCharacter() != null) break;
        }
        return count >= maxReach;
    }

    public static ArrayList<BoardPosition> getRayAccessibleCells(int x, int y, int maxReach, Board board, BoardPosition startPosition) {
        ArrayList<BoardPosition> accessibleCells = new ArrayList<>();
        int currentX = startPosition.x();
        int currentY = startPosition.y();
        int count = 0;
        while (count < maxReach) {
            count++;
            currentX += x;
            currentY += y;
            var currentPosition = new BoardPosition(currentX, currentY);

            if (!board.isValidCell(currentPosition)) break;
            accessibleCells.add(currentPosition);
            if (board.getCell(currentPosition).getCharacter() != null) break;
        }
        return accessibleCells;
    }
}
