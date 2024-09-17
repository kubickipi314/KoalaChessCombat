package com.io.core.board;

public record BoardPosition(int x, int y) {
    public BoardPosition add(int x, int y) {
        return new BoardPosition(this.x + x, this.y + y);
    }
}
