package com.io.core.board;

import com.io.core.character.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CellTest {

    @Test
    void testGetterSetterCharacter() {
        Cell cell = new Cell(false);
        var mockPlayer = Mockito.mock(Player.class);
        cell.setCharacter(mockPlayer);
        var result = cell.getCharacter();
        assertEquals(mockPlayer, result);
    }

    @Test
    void testIsBlocked() {
        boolean value = false;
        Cell cell = new Cell(value);

        assertEquals(value, cell.isBlocked);
    }
}
