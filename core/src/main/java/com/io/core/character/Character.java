package com.io.core.character;

import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.service.TurnService;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Character {
    protected TurnService ts;

    protected final int team;
    protected final List<Move> moves;

    protected int currentMana;
    protected final int maxMana;
    protected int currentHealth;
    protected final int maxHealth;

    protected BoardPosition position;

    public Character(TurnService ts, int maxMana, int maxHealth, BoardPosition position, int team, List<Move> moves) {
        this.ts = ts;
        this.maxMana = maxMana;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.position = position;
        this.team = team;
        this.moves = moves;
    }

    public Move getMove(int idx) {
        return moves.get(idx);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public abstract void startTurn();


    public int getCurrentMana() {
        return currentMana;
    }

    public void changeMana(int value) {
        currentMana = max(min(maxMana, currentMana + value), 0);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void changeHealth(int value) {
        currentHealth = max(min(maxHealth, currentHealth + value), 0);
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
