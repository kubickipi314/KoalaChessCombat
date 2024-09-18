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

class BishopMoveTest {

    Board smallBoard;

    @Test
    void testIsValidMove_NoObstacle() {
        BishopMove bishopMove = new BishopMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(bishopMove.isMoveValid(mockPlayer, new BoardPosition(3, 3), mockBoard));
    }

    @Test
    void testIsValid_Obstacle() {
        BishopMove bishopMove = new BishopMove(10, 5);

        var mockBoard = mock(Board.class);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(2, 2))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(bishopMove.isMoveValid(mockPlayer, new BoardPosition(3, 3), mockBoard));
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
        BishopMove bishopMove = new BishopMove(10, 5);
        init();

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = bishopMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(1, result.size(), "There should be one accessible cells.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        BishopMove bishopMove = new BishopMove(10, 5);
        init();
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(1, 1))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = bishopMove.getAccessibleCells(mockPlayer, smallBoard);

        assertEquals(1, result.size(), "There should be 1 accessible cells.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
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