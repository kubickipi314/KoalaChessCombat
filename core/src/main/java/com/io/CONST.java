package com.io;

public class CONST {
    private CONST() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }

    public static final int DEFAULT_ROOM_WIDTH = 8;
    public static final int DEFAULT_ROOM_HEIGHT = 8;
    public static final int MAX_PLAYER_MANA = 12;
    public static final int DEFAULT_INCREASE_AMOUNT = 5;
    public static final int MAX_PLAYER_HEALTH = 10;
}
