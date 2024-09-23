package com.io;

public final class CONST {
    private CONST() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }

    public static final int DEFAULT_ROOM_WIDTH = 5;
    public static final int DEFAULT_ROOM_HEIGHT = 5;
    public static final int MAX_PLAYER_MANA = 12;
    public static final int DEFAULT_INCREASE_AMOUNT = 5;
    public static final int MAX_PLAYER_HEALTH = 10;

    public static final int PLAYER_TEAM = 0;
    public static final int ENEMY_TEAM = 1;
}
