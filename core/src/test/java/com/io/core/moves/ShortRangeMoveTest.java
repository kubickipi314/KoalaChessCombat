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

class ShortRangeMoveTest {

    Board smallBoard;

    @Test
    void testIsValidMove_NoObstacle() {
        ShortRangeMove shortRangeMove = new ShortRangeMove(10, 5, 2);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(shortRangeMove.isMoveValid(mockPlayer, new BoardPosition(0, 3), mockBoard));
        assertFalse(shortRangeMove.isMoveValid(mockPlayer, new BoardPosition(3, 0), mockBoard));
        assertFalse(shortRangeMove.isMoveValid(mockPlayer, new BoardPosition(3, 3), mockBoard));
        assertTrue(shortRangeMove.isMoveValid(mockPlayer, new BoardPosition(1, 1), mockBoard));
    }

    @Test
    void testIsValid_Obstacle() {
        ShortRangeMove shortRangeMove = new ShortRangeMove(10, 5, 3);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(2, 2))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(0, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(1, 0))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(shortRangeMove.isMoveValid(mockPlayer, new BoardPosition(2, 2), mockBoard));
        assertFalse(shortRangeMove.isMoveValid(mockPlayer, new BoardPosition(1, 0), mockBoard));
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
        ShortRangeMove shortRangeMove = new ShortRangeMove(10, 5, 2);
        init();

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = shortRangeMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(8, result.size(), "There should be nine accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 2)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 2)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(2, 1)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        ShortRangeMove shortRangeMove = new ShortRangeMove(10, 5, 1);
        init();
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(0, 2))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = shortRangeMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(3, result.size(), "There should be five accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
        assertFalse(result.contains(new BoardPosition(0, 0)), "position should be accessible.");
    }


    @Test
    void getType() {
        ShortRangeMove shortRangeMove = new ShortRangeMove(10, 5, 2);

        var type = shortRangeMove.getType();

        assertEquals(MoveType.SHORT_RANGE, type, "The type should match the value of BISHOP enum");
    }

    @Test
    public void testGetCost() {
        ShortRangeMove shortRangeMove = new ShortRangeMove(10, 5, 2);

        int cost = shortRangeMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        ShortRangeMove shortRangeMove = new ShortRangeMove(10, 5, 2);

        int damage = shortRangeMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}