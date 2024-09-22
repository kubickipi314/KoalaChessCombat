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

class KingMoveTest {

    Board smallBoard;
    Player player;

    @Test
    void testIsValidMove_NoObstacle() {
        var mockBoard = mock(Board.class);
        KingMove kingMove = new KingMove(10, 5, mockBoard);


        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(kingMove.isMoveValid(mockPlayer, new BoardPosition(1, 1)));
        assertTrue(kingMove.isMoveValid(mockPlayer, new BoardPosition(0, 1)));
        assertTrue(kingMove.isMoveValid(mockPlayer, new BoardPosition(1, 0)));
        assertFalse(kingMove.isMoveValid(mockPlayer, new BoardPosition(2, 2)));
    }

    @Test
    void testIsValid_Obstacle() {
        var mockBoard = mock(Board.class);
        KingMove kingMove = new KingMove(10, 5, mockBoard);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(1, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(0, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(1, 0))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertFalse(kingMove.isMoveValid(mockPlayer, new BoardPosition(1, 1)));
        assertFalse(kingMove.isMoveValid(mockPlayer, new BoardPosition(1, 0)));
    }

    void init() {
        player = mock(Player.class);
        when(player.getPosition()).thenReturn(new BoardPosition(0, 0));
        when(player.getTeam()).thenReturn(0);
        smallBoard = mock(Board.class);
        when(smallBoard.isValidCell(any(BoardPosition.class))).thenReturn(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(smallBoard.isValidCell(new BoardPosition(i, j))).thenReturn(true);
            }
        }
        when(smallBoard.getBoardHeight()).thenReturn(3);
        when(smallBoard.getBoardWidth()).thenReturn(3);
        when(smallBoard.getCharacter(new BoardPosition(0, 0))).thenReturn(player);
    }

    @Test
    void testGetAccessibleCells_NoObstacle() {
        init();
        KingMove kingMove = new KingMove(10, 5, smallBoard);


        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = kingMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(3, result.size(), "There should be three accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        init();
        KingMove kingMove = new KingMove(10, 5, smallBoard);

        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(1, 1))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = kingMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(3, result.size(), "There should be three accessible cells.");
        assertTrue(result.contains(new BoardPosition(0, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 0)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 1)), "position should be accessible.");
    }


    @Test
    void getType() {
        KingMove kingMove = new KingMove(10, 5, null);

        var type = kingMove.getType();

        assertEquals(MoveType.KING, type, "The type should match the value of BISHOP enum");
    }

    @Test
    public void testGetCost() {
        KingMove kingMove = new KingMove(10, 5, null);

        int cost = kingMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        KingMove kingMove = new KingMove(10, 5, null);

        int damage = kingMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}
