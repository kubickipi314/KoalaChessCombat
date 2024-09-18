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

class RookMoveTest {

    Board smallBoard;

    @Test
    void testIsValidMove_NoObstacle() {
        RookMove rookMove = new RookMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(rookMove.isMoveValid(mockPlayer, new BoardPosition(0, 3), mockBoard));
    }

    @Test
    void testIsValid_Obstacle() {
        RookMove rookMove = new RookMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(0, 2))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(rookMove.isMoveValid(mockPlayer, new BoardPosition(0, 3), mockBoard));
    }

    void init() {
        smallBoard = mock(Board.class);
        when(smallBoard.isValidCell(any(BoardPosition.class))).thenReturn(false);
        when(smallBoard.isValidCell(new BoardPosition(0, 0))).thenReturn(true);
        when(smallBoard.isValidCell(new BoardPosition(1, 0))).thenReturn(true);
        when(smallBoard.isValidCell(new BoardPosition(0, 1))).thenReturn(true);
        when(smallBoard.isValidCell(new BoardPosition(1, 1))).thenReturn(true);
        when(smallBoard.getBoardHeight()).thenReturn(2);
        when(smallBoard.getBoardWidth()).thenReturn(2);
    }

    @Test
    void testGetAccessibleCells_NoObstacle() {
        RookMove rookMove = new RookMove(10, 5);
        init();

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = rookMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        RookMove rookMove = new RookMove(10, 5);
        init();
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(0, 1))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = rookMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
    }


    @Test
    void getType() {
        RookMove rookMove = new RookMove(10, 5);

        var type = rookMove.getType();

        assertEquals(MoveType.ROOK, type, "The type should match the value of ROOK enum");
    }

    @Test
    public void testGetCost() {
        RookMove rookMove = new RookMove(10, 5);

        int cost = rookMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        RookMove rookMove = new RookMove(10, 5);

        int damage = rookMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}