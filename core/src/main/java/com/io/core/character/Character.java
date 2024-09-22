package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.db.entity.CharacterEntity;
import com.io.service.CharacterInterface;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Character implements CharacterInterface {
    protected final int team;
    protected int currentMana;
    protected final int maxMana;
    protected int currentHealth;
    protected final int maxHealth;
    protected Board board;

    protected BoardPosition position;

    public Character(int maxMana, int maxHealth, BoardPosition position, int team, Board board) {
        this.maxMana = maxMana;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.position = position;
        this.team = team;
        this.board = board;
    }

    public Character(int maxMana, int maxHealth, CharacterEntity che, Board board) {
        this(maxMana, maxHealth, che.getPosition(), che.getTeam(), board);
        this.currentHealth = che.getCurrentHealth() != null ? che.getCurrentHealth() : maxHealth;
        this.currentMana = che.getCurrentMana() != null ? che.getCurrentMana() : maxHealth;
    }


    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return currentMana;
    }

    public void changeMana(int value) {
        currentMana = max(min(maxMana, currentMana + value), 0);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public boolean isDead() {
        return currentHealth <= 0;
    }

}
