package com.io.core.character;

import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.service.TurnService;

import java.util.List;

public abstract class Character {
    protected TurnService ts;

    protected final int team;
    protected final List<Move> moves;

    protected int currentMana;
    protected int maxMana;
    protected int currentHealth;
    protected int maxHealth;

    protected BoardPosition position;

    public Character(TurnService ts, int maxMana, int maxHealth, BoardPosition position, int team, List<Move> moves) {
        this.ts = ts;
        this.maxMana = maxMana;
        this.maxHealth = maxHealth;
        this.position = position;
        this.team = team;
        this.moves = moves;
    }

    public Move getMove(int idx) {
        return moves.get(idx);
    }

    public abstract void startTurn();


    public int getCurrentMana() {
        return currentMana;
    }

    public void increaseMana(int value) {
        if (value >= 0)
            currentMana = Math.max(maxMana, currentMana + value);
        else
            currentMana = currentMana + value;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void increaseHealth(int value) {
        if (value >= 0)
            currentHealth = Math.max(maxHealth, currentHealth + value);
        else
            currentHealth += value;
    }

    public BoardPosition getPosition() {
        return position;
    }

    public void setPosition(BoardPosition position) {
        this.position = position;
    }

    public int getTeam() {
        return team;
    }
}
