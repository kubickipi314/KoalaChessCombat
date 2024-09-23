package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CharacterTest {
    Board board;

    private void init() {
        board = mock(Board.class);
    }

    @Test
    public void testMana() {
        init();
        var character = new Player(new BoardPosition(0, 0), List.of(), board);
        assertEquals(character.getMaxMana(), CONST.MAX_PLAYER_MANA);
        assertEquals(character.getCurrentMana(), CONST.MAX_PLAYER_MANA);
        character.changeMana(1);
        assertEquals(character.getCurrentMana(), CONST.MAX_PLAYER_MANA);
        character.changeMana(-1);
        assertEquals(character.getCurrentMana(), CONST.MAX_PLAYER_MANA - 1);
    }

    @Test
    public void testHealth() {
        init();
        var character = new Player(new BoardPosition(0, 0), List.of(), board);
        assertEquals(character.getMaxHealth(), CONST.MAX_PLAYER_HEALTH);
        assertEquals(character.getCurrentHealth(), CONST.MAX_PLAYER_HEALTH);
        character.changeHealth(1);
        assertEquals(character.getCurrentHealth(), CONST.MAX_PLAYER_HEALTH);
        character.changeHealth(-1);
        assertEquals(character.getCurrentHealth(), CONST.MAX_PLAYER_HEALTH - 1);
    }

    @Test
    public void testPosition() {
        init();
        var character = new Player(new BoardPosition(1, 0), List.of(), board);
        assertEquals(character.getPosition(), new BoardPosition(1, 0));
        character.setPosition(new BoardPosition(2, 3));
        assertEquals(character.getPosition(), new BoardPosition(2, 3));
    }

    @Test
    public void testGetTeam() {
        init();
        var character = new Player(new BoardPosition(0, 0), List.of(), board);
        assertEquals(character.team, CONST.PLAYER_TEAM);
    }

    @Test
    public void testIsDead() {
        init();
        var character = new Player(new BoardPosition(0, 0), List.of(), board);
        assertFalse(character.isDead());
        character.changeHealth(-1000);
        assertTrue(character.isDead());
    }

    @Test
    public void testGetType() {
        init();
        var character = new Player(new BoardPosition(0, 0), List.of(), board);
        assertEquals(character.getType(), CharacterEnum.Player);
    }
}
