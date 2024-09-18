package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.board.Cell;
import com.io.core.character.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BishopMoveTest {

    @Test
    void testIsValidMove_NoObstacle() {
        BishopMove bishopMove = new BishopMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.getCell(any(BoardPosition.class))).thenReturn(new Cell(false));
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPostion = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPostion);


        assertTrue(bishopMove.isMoveValid(mockPlayer, new BoardPosition(3, 3), mockBoard));
    }

    @Test
    void getAccessibleCells_Obstacle() {
        BishopMove bishopMove = new BishopMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.getCell(any(BoardPosition.class))).thenReturn(new Cell(false));
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(2, 2))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(bishopMove.isMoveValid(mockPlayer, new BoardPosition(3, 3), mockBoard));
    }

    @Test
    void getType() {
        BishopMove bishopMove = new BishopMove(10, 5);

        var type = bishopMove.getType();

        assertEquals(type, MoveType.BISHOP, "The type should match the value of BISHOP enum");
    }

    @Test
    public void testGetCost() {
        BishopMove bishopMove = new BishopMove(10, 5);

        int cost = bishopMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        BishopMove bishopMove = new BishopMove(10, 5);

        int damage = bishopMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}