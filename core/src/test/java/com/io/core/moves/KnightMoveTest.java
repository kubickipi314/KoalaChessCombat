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
    Player player;

    @Test
    void testIsValidMove_NoObstacle() {
        var mockBoard = mock(Board.class);
        KnightMove knightMove = new KnightMove(10, 5, mockBoard);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(knightMove.isMoveValid(mockPlayer, new BoardPosition(1, 2)));
    }

    @Test
    void testIsValid_Obstacle() {
        var mockBoard = mock(Board.class);
        KnightMove knightMove = new KnightMove(10, 5, mockBoard);
        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.isValidCell(new BoardPosition(1, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(0, 1))).thenReturn(false);
        when(mockBoard.isValidCell(new BoardPosition(1, 0))).thenReturn(false);
        var startPosition = new BoardPosition(0, 0);
        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);


        assertTrue(knightMove.isMoveValid(mockPlayer, new BoardPosition(2, 1)));
        assertTrue(knightMove.isMoveValid(mockPlayer, new BoardPosition(1, 2)));
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
        KnightMove knightMove = new KnightMove(10, 5, smallBoard);


        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = knightMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(2, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 2)), "position should be accessible.");
    }

    @Test
    void testGetAccessibleCells_Obstacle() {
        init();
        KnightMove knightMove = new KnightMove(10, 5, smallBoard);
        var mockEnemy = mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);
        when(smallBoard.getCharacter(new BoardPosition(2, 1))).thenReturn(mockEnemy);

        var mockPlayer = mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(new BoardPosition(0, 0));

        var result = knightMove.getAccessibleCells(new BoardPosition(0, 0));

        assertEquals(2, result.size(), "There should be two accessible cells.");
        assertTrue(result.contains(new BoardPosition(2, 1)), "position should be accessible.");
        assertTrue(result.contains(new BoardPosition(1, 2)), "position should be accessible.");
    }


    @Test
    void getType() {
        KnightMove knightMove = new KnightMove(10, 5, null);

        var type = knightMove.getType();

        assertEquals(MoveType.KNIGHT, type, "The type should match the value of KNIGHT enum");
    }

    @Test
    public void testGetCost() {
        KnightMove knightMove = new KnightMove(10, 5, null);

        int cost = knightMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        KnightMove knightMove = new KnightMove(10, 5, null);

        int damage = knightMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}
