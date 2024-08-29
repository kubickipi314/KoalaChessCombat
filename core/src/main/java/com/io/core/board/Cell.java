package com.io.core.board;

import com.io.core.character.Character;

public class Cell {
    public final boolean isBlocked;
    private Character character;

    public Cell(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
