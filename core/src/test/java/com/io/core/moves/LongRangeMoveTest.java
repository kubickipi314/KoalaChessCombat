package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.MeleeEnemy;
import com.io.core.character.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LongRangeMoveTest {

    Board smallBoard;

    @Test
    void testIsValidMove_NoObstacle() {
        LongRangeMove longRangeMove = new LongRangeMove(10, 5, 2);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(longRangeMove.isMoveValid(mockPlayer, new BoardPosition(0, 2), mockBoard));
        assertTrue(longRangeMove.isMoveValid(mockPlayer, new BoardPosition(2, 0), mockBoard));
        assertTrue(longRangeMove.isMoveValid(mockPlayer, new BoardPosition(2, 2), mockBoard));
        assertFalse(longRangeMove.isMoveValid(mockPlayer, new BoardPosition(1, 1), mockBoard));
    }

    @Test
    void testIsValid_Obstacle() {
        LongRangeMove longRangeMove = new LongRangeMove(10, 5, 2);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(2, 2))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(0, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(1, 0))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(longRangeMove.isMoveValid(mockPlayer, new BoardPosition(2, 2), mockBoard));
        assertFalse(longRangeMove.isMoveValid(mockPlayer, new BoardPosition(1, 0), mockBoard));
    }

    void init() {
        smallBoard = mock(Board.class);
        when(smallBoard.isValidCell(any(BoardPosition.class))).thenReturn(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(smallBoard.isValidCell(new BoardPosition(i, j))).thenReturn(true);
            }
        }
        when(smallBoard.getBoardHeight()).thenReturn(3);
        when(smallBoard.getBoardWidth()).thenReturn(3);
    }

    @Test
    void testGetAccessibleCells_NoObstacle() {
        LongRangeMove longRangeMove = new LongRangeMove(10, 5, 2);
        init();

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = longRangeMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(5, result.size(), "There should be five accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 2)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 2)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(2, 1)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        LongRangeMove longRangeMove = new LongRangeMove(10, 5, 2);
        init();
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(0, 2))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = longRangeMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(5, result.size(), "There should be five accessible cells.");
        assertTrue(result.contains(new BoardPosition(2, 2)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 2)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(2, 1)), "position should be accessible.");
    }


    @Test
    void getType() {
        LongRangeMove longRangeMove = new LongRangeMove(10, 5, 2);

        var type = longRangeMove.getType();

        assertEquals(MoveType.LONG_RANGE, type, "The type should match the value of BISHOP enum");
    }

    @Test
    public void testGetCost() {
        LongRangeMove longRangeMove = new LongRangeMove(10, 5, 2);

        int cost = longRangeMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        LongRangeMove longRangeMove = new LongRangeMove(10, 5, 2);

        int damage = longRangeMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}