package com.io.core.moves;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.board.Cell;
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
        Cell mockCell = Mockito.mock(Cell.class);
        Enemy mockEnemy = Mockito.mock(MeleeEnemy.class);

        PawnMove pawnMove = new PawnMove(10, 5);

        when(mockBoard.isValidCell(startPosition)).thenReturn(true);
        when(mockBoard.isValidCell(endPosition)).thenReturn(true);
        when(mockBoard.getCell(endPosition)).thenReturn(mockCell);
        when(mockCell.getCharacter()).thenReturn(mockEnemy);

        boolean result = pawnMove.isMoveValid(mockPlayer, endPosition, mockBoard);

        assertFalse(result, "The move should be valid.");
    }

    @Test
    public void testIsMoveValid_Enemy() {
        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition startPosition = new BoardPosition(1, 1);
        BoardPosition endPosition = new BoardPosition(2, 2);
        Player mockPlayer = Mockito.mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(startPosition);
        when(mockPlayer.getTeam()).thenReturn(0);
        Cell mockCell = Mockito.mock(Cell.class);
        Enemy mockEnemy = Mockito.mock(MeleeEnemy.class);
        when(mockEnemy.getTeam()).thenReturn(1);

        PawnMove pawnMove = new PawnMove(10, 5);

        when(mockBoard.isValidCell(startPosition)).thenReturn(true);
        when(mockBoard.isValidCell(endPosition)).thenReturn(true);
        when(mockBoard.getCell(endPosition)).thenReturn(mockCell);
        when(mockCell.getCharacter()).thenReturn(mockEnemy);

        boolean result = pawnMove.isMoveValid(mockPlayer, endPosition, mockBoard);

        assertTrue(result, "The move should be valid.");
    }

    @Test
    public void testGetAccessibleCells() {
        Board mockBoard = Mockito.mock(Board.class);
        BoardPosition position = new BoardPosition(1, 1);
        Cell mockCell = Mockito.mock(Cell.class);

        PawnMove pawnMove = new PawnMove(10, 5);

        var mockEnemy = Mockito.mock(MeleeEnemy.class);
        when(mockCell.getCharacter()).thenReturn(mockEnemy);
        when(mockEnemy.getTeam()).thenReturn(1);


        when(mockBoard.isValidCell(any(BoardPosition.class))).thenReturn(true);
        when(mockBoard.getCell(any(BoardPosition.class))).thenReturn(new Cell(false));
        when(mockBoard.getCell(new BoardPosition(2, 2))).thenReturn(mockCell);

        Player mockPlayer = Mockito.mock(Player.class);
        when(mockPlayer.getPosition()).thenReturn(position);
        when(mockPlayer.getTeam()).thenReturn(0);

        List<BoardPosition> accessibleCells = pawnMove.getAccessibleCells(mockPlayer, mockBoard);

        assertEquals(2, accessibleCells.size(), "There should be two accessible cells.");
        assertTrue(accessibleCells.contains(new BoardPosition(1, 2)), "Front-left position should be accessible.");
        assertTrue(accessibleCells.contains(new BoardPosition(2, 2)), "Front-right position should be accessible.");
    }

    @Test
    public void testGetCost() {
        PawnMove pawnMove = new PawnMove(10, 5);

        int cost = pawnMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        PawnMove pawnMove = new PawnMove(10, 5);

        int damage = pawnMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}