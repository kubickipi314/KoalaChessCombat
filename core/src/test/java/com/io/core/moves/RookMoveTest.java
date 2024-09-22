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
    Player player;

    @Test
    void testIsValidMove_NoObstacle() {
        var mockBoard = mock(Board.class);
        RookMove rookMove = new RookMove(10, 5, mockBoard);


        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(rookMove.isMoveValid(mockPlayer, new BoardPosition(0, 3)));
    }

    @Test
    void testIsValid_Obstacle() {
        var mockBoard = mock(Board.class);
        RookMove rookMove = new RookMove(10, 5, mockBoard);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(0, 2))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(rookMove.isMoveValid(mockPlayer, new BoardPosition(0, 3)));
    }

    void init() {
        smallBoard = mock(Board.class);
        player = mock(Player.class);
        when(player.getPosition()).thenReturn(new BoardPosition(0, 0));
        when(player.getTeam()).thenReturn(0);
        when(smallBoard.isValidCell(any(BoardPosition.class))).thenReturn(false);
        when(smallBoard.isValidCell(new BoardPosition(0, 0))).thenReturn(true);
        when(smallBoard.isValidCell(new BoardPosition(1, 0))).thenReturn(true);
        when(smallBoard.isValidCell(new BoardPosition(0, 1))).thenReturn(true);
        when(smallBoard.isValidCell(new BoardPosition(1, 1))).thenReturn(true);
        when(smallBoard.getBoardHeight()).thenReturn(2);
        when(smallBoard.getBoardWidth()).thenReturn(2);
        when(smallBoard.getCharacter(new BoardPosition(0, 0))).thenReturn(player);
    }

    @Test
    void testGetAccessibleCells_NoObstacle() {
        init();
        RookMove rookMove = new RookMove(10, 5, smallBoard);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = rookMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        init();
        RookMove rookMove = new RookMove(10, 5, smallBoard);
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(0, 1))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = rookMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
    }


    @Test
    void getType() {
        RookMove rookMove = new RookMove(10, 5, null);

        var type = rookMove.getType();

        assertEquals(MoveType.ROOK, type, "The type should match the value of ROOK enum");
    }

    @Test
    public void testGetCost() {
        RookMove rookMove = new RookMove(10, 5, null);

        int cost = rookMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        RookMove rookMove = new RookMove(10, 5, null);

        int damage = rookMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}
