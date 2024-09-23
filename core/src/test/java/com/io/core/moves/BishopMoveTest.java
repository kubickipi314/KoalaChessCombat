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
    Player player;

    @Test
    void testIsValidMove_NoObstacle() {
        var mockBoard = mock(Board.class);
        BishopMove bishopMove = new BishopMove(10, 5, mockBoard);


        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(bishopMove.isMoveValid(mockPlayer, new BoardPosition(3, 3)));
    }

    @Test
    void testIsValid_Obstacle() {
        var mockBoard = mock(Board.class);
        BishopMove bishopMove = new BishopMove(10, 5, mockBoard);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(2, 2))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(bishopMove.isMoveValid(mockPlayer, new BoardPosition(3, 3)));
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
        BishopMove bishopMove = new BishopMove(10, 5, smallBoard);

        var result = bishopMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(1, result.size(), "There should be one accessible cells.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        init();
        BishopMove bishopMove = new BishopMove(10, 5, smallBoard);
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(1, 1))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = bishopMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(1, result.size(), "There should be 1 accessible cells.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
    }


    @Test
    void getType() {
        BishopMove bishopMove = new BishopMove(10, 5, smallBoard);

        var type = bishopMove.getType();

        assertEquals(MoveType.BISHOP, type, "The type should match the value of BISHOP enum");
    }

    @Test
    public void testGetCost() {
        BishopMove bishopMove = new BishopMove(10, 5, smallBoard);

        int cost = bishopMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        BishopMove bishopMove = new BishopMove(10, 5, smallBoard);

        int damage = bishopMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}
