package com.io.core;

import com.io.service.TurnService;

public abstract class Character {
    TurnService ts;

    int currentMana;
    int maxMana;
    int currentHealth;
    int maxHealth;

    BoardPosition position;

    MoveType[] moves;
    int team;

    public Character(TurnService ts, int maxMana, int maxHealth, BoardPosition position) {
        this.ts = ts;
        this.maxMana = maxMana;
        this.maxHealth = maxHealth;
        this.position = position;
    }

    public MoveType getMoveType(int idx) {
        if (idx < 0 || idx >= moves.length)
            return null;
        return moves[idx];
    }

    public int getTeam() {
        return team;
    }

    public void increaseMana(int value) {
        if (value >= 0)
            currentMana = Math.max(maxMana, currentMana + value);
        else
            currentMana = currentMana + value;
    }

    public void increaseHealth(int value) {
        if (value >= 0)
            currentHealth = Math.max(maxHealth, currentHealth + value);
        else
            currentHealth += value;
    }

    public abstract void startTurn();
}
