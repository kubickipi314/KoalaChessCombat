package com.io.core.board;

import com.io.core.character.Character;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

    @Test
    void testTryMakeMove() {
    }

    @Test
    void testGetBoardWidth() {
        Board board = new Board(10, 20, new ArrayList<>(), List.of());
        int result = board.getBoardWidth();
        assertEquals(10, result);
    }

    @Test
    void testGetBoardHeight() {
        Board board = new Board(10, 20, new ArrayList<>(), List.of());
        int result = board.getBoardHeight();
        assertEquals(20, result);
    }

    @Test
    void testIsValidCell() {
    }

    @Test
    void testGetCharacter() {
    }

    @Test
    void testGetCharacters() {
        List<Character> mockList = new ArrayList<>();
        Board board = new Board(10, 20, mockList, List.of());
        var result = board.getCharacters();
        assertEquals(result, mockList);
    }

    @Test
    void testGetTeamPosition() {
    }

    @Test
    void testDecreaseTeamCount() {
    }

    @Test
    void testGetTeamCount() {
    }
}
