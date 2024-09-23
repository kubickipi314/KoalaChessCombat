package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Enemy;
import com.io.core.character.MeleeEnemy;
import com.io.core.character.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class PawnMoveTest {

    @Test
    public void testIsMoveValid_Player() {
        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition startPosition = new BoardPosition(1, 1);
        BoardPosition endPosition = new BoardPosition(2, 2);
        Player mockPlayer = Mockito.mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);
        Enemy mockEnemy = Mockito.mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(0);//player group

        PawnMove pawnMove = new PawnMove(10, 5, mockBoard);

        when(mockBoard.isValidCell(startPosition)).thenReturn(true);
        when(mockBoard.isValidCell(endPosition)).thenReturn(true);
        when(mockBoard.getCharacter(endPosition)).thenReturn(mockEnemy);

        boolean result = pawnMove.isMoveValid(mockPlayer, endPosition);

        assertFalse(result, "The move should be not valid.");
    }

    @Test
    public void testIsMoveValid_Enemy() {
        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition startPosition = new BoardPosition(1, 1);
        BoardPosition endPosition = new BoardPosition(2, 2);
        Player mockPlayer = Mockito.mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);
        when(mockPlayer.getTeam()).thenReturn(0);
        Enemy mockEnemy = Mockito.mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);

        PawnMove pawnMove = new PawnMove(10, 5, mockBoard);

        when(mockBoard.isValidCell(startPosition)).thenReturn(true);
        when(mockBoard.isValidCell(endPosition)).thenReturn(true);
        when(mockBoard.getCharacter(endPosition)).thenReturn(mockEnemy);

        boolean result = pawnMove.isMoveValid(mockPlayer, endPosition);

        assertTrue(result, "The move should be valid.");
    }

    @Test
    public void testGetAccessibleCells() {
        BoardPosition position = new BoardPosition(1, 1);
        Player mockPlayer = Mockito.mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(position);
        when(mockPlayer.getTeam()).thenReturn(0);
        Board mockBoard = Mockito.mock(Board.class);
        when(mockBoard.getCharacter(position)).thenReturn(mockPlayer);


        PawnMove pawnMove = new PawnMove(10, 5, mockBoard);

        var mockEnemy = Mockito.mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);


        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.getCharacter(new BoardPosition(2, 2))).thenReturn(mockEnemy);


        List<BoardPosition> accessibleCells = pawnMove.getAccessibleCells(position);
        assertEquals(2, accessibleCells.size(), "There should be two accessible cells.");
        assertTrue(accessibleCells.contains(new BoardPosition(1, 2)), "Front-left position should be accessible.");
        assertTrue(accessibleCells.contains(new BoardPosition(2, 2)), "Front-right position should be accessible.");
    }

    @Test
    public void testGetCost() {
        PawnMove pawnMove = new PawnMove(10, 5, null);

        int cost = pawnMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        PawnMove pawnMove = new PawnMove(10, 5, null);

        int damage = pawnMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }

    @Test
    void getType() {
        PawnMove pawnMove = new PawnMove(10, 5, null);

        var type = pawnMove.getType();

        assertEquals(MoveType.PAWN, type, "The type should match the value of BISHOP enum");
    }
}
