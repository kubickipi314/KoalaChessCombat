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

class QueenMoveTest {

    Board smallBoard;
    Player player;

    @Test
    void testIsValidMove_NoObstacle() {
        var mockBoard = mock(Board.class);
        QueenMove queenMove = new QueenMove(10, 5, mockBoard);


        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(queenMove.isMoveValid(mockPlayer, new BoardPosition(0, 3)));
    }

    @Test
    void testIsValid_Obstacle() {
        var mockBoard = mock(Board.class);
        QueenMove queenMove = new QueenMove(10, 5, mockBoard);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(2, 2))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(queenMove.isMoveValid(mockPlayer, new BoardPosition(3, 3)));
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
        QueenMove queenMove = new QueenMove(10, 5, smallBoard);

        var result = queenMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(3, result.size(), "There should be three accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        init();
        QueenMove queenMove = new QueenMove(10, 5, smallBoard);
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(0, 1))).thenReturn(mockEnemy);

        var result = queenMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(3, result.size(), "There should be three accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
    }


    @Test
    void getType() {
        QueenMove queenMove = new QueenMove(10, 5, null);

        var type = queenMove.getType();

        assertEquals(MoveType.QUEEN, type, "The type should match the value of QUEEN enum");
    }

    @Test
    public void testGetCost() {
        QueenMove queenMove = new QueenMove(10, 5, null);

        int cost = queenMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        QueenMove queenMove = new QueenMove(10, 5, null);

        int damage = queenMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}
