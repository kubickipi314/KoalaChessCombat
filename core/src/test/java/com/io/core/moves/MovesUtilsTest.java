package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.board.Cell;
import com.io.core.character.Character;
import com.io.core.character.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


class MovesUtilsTest {

    @Test
    public void testIsValidRayMove_ValidMove() {
        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition startPosition = new BoardPosition(0, 0);
        BoardPosition endPosition = new BoardPosition(2, 2);
        int x = 1, y = 1, maxReach = 3;

        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);

        boolean result = MovesUtils.isValidRayMove(x, y, maxReach, startPosition, endPosition, mockBoard);

        assertTrue(result, "The ray move should be valid.");
    }

    @Test
    public void testIsValidRayMove_InvalidMove_OutOfBounds() {

        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition startPosition = new BoardPosition(0, 0);
        BoardPosition endPosition = new BoardPosition(4, -4);
        int x = 1, y = -1, maxReach = 3;

        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        boolean result = MovesUtils.isValidRayMove(x, y, maxReach, startPosition, endPosition, mockBoard);

        assertFalse(result, "The ray move should be invalid due to out-of-bounds.");
    }

    @Test
    public void testIsValidRayMove_InvalidMove_BlockingCharacter() {
        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition startPosition = new BoardPosition(4, 4);
        BoardPosition endPosition = new BoardPosition(0, 0);
        int x = -1, y = -1, maxReach = Integer.MAX_VALUE;

        Cell blockingCell = Mockito.mock(Cell.class);
        when(blockingCell.getCharacter()).thenReturn(Mockito.mock(Player.class));

        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(2, 2))).thenReturn(false);

        boolean result = MovesUtils.isValidRayMove(x, y, maxReach, startPosition, endPosition, mockBoard);

        assertFalse(result, "The ray move should be invalid due to a blocking character.");
    }

    @Test
    public void testGetRayAccessibleCells_NoObstacles() {
        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition startPosition = new BoardPosition(0, 0);
        int x = 1, y = 1, maxReach = 3;

        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);

        List<BoardPosition> result = MovesUtils.getRayAccessibleCells(x, y, maxReach, mockBoard, startPosition);

        assertEquals(3, result.size(), "There should be three accessible cells.");
        assertTrue(result.contains(new BoardPosition(1, 1)));
        assertTrue(result.contains(new BoardPosition(2, 2)));
        assertTrue(result.contains(new BoardPosition(3, 3)));
    }

    @Test
    public void testGetRayAccessibleCells_WithObstacles() {
        Board mockBoard = Mockito.mock(Board.class);
        Character mockCharacter = Mockito.mock(Player.class);
        BoardPosition startPosition = new BoardPosition(0, 0);
        int x = 1, y = 1, maxReach = 3;

        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(1, 1))).thenReturn(true);
        when(mockBoard.getCharacter(new BoardPosition(2, 2))).thenReturn(mockCharacter);

        List<BoardPosition> result = MovesUtils.getRayAccessibleCells(x, y, maxReach, mockBoard, startPosition);

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "The first position should be accessible.");
        assertTrue(result.contains(new BoardPosition(2, 2)), "The second position should be accessible but blocked.");
    }
}