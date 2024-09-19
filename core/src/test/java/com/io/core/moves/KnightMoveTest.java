package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.MeleeEnemy;
import com.io.core.character.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KnightMoveTest {

    Board smallBoard;

    @Test
    void testIsValidMove_NoObstacle() {
        KnightMove knightMove = new KnightMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(knightMove.isMoveValid(mockPlayer, new BoardPosition(1, 2), mockBoard));
    }

    @Test
    void testIsValid_Obstacle() {
        KnightMove knightMove = new KnightMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(1, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(0, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(1, 0))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(knightMove.isMoveValid(mockPlayer, new BoardPosition(2, 1), mockBoard));
        assertTrue(knightMove.isMoveValid(mockPlayer, new BoardPosition(1, 2), mockBoard));
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
        KnightMove knightMove = new KnightMove(10, 5);
        init();

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = knightMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(2, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 2)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        KnightMove knightMove = new KnightMove(10, 5);
        init();
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(2, 1))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = knightMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(2, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 2)), "position should be accessible.");
    }


    @Test
    void getType() {
        KnightMove knightMove = new KnightMove(10, 5);

        var type = knightMove.getType();

        assertEquals(MoveType.KNIGHT, type, "The type should match the value of KNIGHT enum");
    }

    @Test
    public void testGetCost() {
        KnightMove knightMove = new KnightMove(10, 5);

        int cost = knightMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        KnightMove knightMove = new KnightMove(10, 5);

        int damage = knightMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}