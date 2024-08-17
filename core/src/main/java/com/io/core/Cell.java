package com.io.core;

public class Cell {
    private final boolean isBlocked;
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

    public boolean isBlocked() {
        return isBlocked;
    }
}
