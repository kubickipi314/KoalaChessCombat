package com.io.core;

public class Cell {
    private final boolean isBlocked;
    private Character occupant;

    public Cell(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
}
