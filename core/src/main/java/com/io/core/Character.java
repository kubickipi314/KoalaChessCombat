package com.io.core;

import com.io.service.TurnService;

public abstract class Character {
    TurnService ts;

    int currentMana;
    int maxMana;
    int currentHealth;
    int maxHealth;

    BoardPosition boardPosition;

    MoveType[] moves;
    int team;

    public Character(TurnService ts) {
        this.ts = ts;
    }

    public MoveType getMoveType(int idx) {
        if (idx < 0 || idx >= moves.length)
            return null;
        return moves[idx];
    }

    public int getTeam() {
        return team;
    }
}
