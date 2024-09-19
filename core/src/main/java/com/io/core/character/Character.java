package com.io.core.character;

import com.io.core.board.BoardPosition;
import com.io.db.entity.CharacterEntity;
import com.io.presenter.GamePresenter;
import com.io.service.GameService;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Character {
    protected GameService gs;
    protected GamePresenter gp;

    protected final int team;

    protected int currentMana;
    protected final int maxMana;
    protected int currentHealth;
    protected final int maxHealth;

    protected BoardPosition position;

    public Character(GameService gs, GamePresenter gp, int maxMana, int maxHealth, BoardPosition position, int team) {
        this.gs = gs;
        this.gp = gp;

        this.maxMana = maxMana;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.position = position;
        this.team = team;
    }

    public Character(GameService gs, GamePresenter gp, int maxMana, int maxHealth, CharacterEntity che) {
        this(gs, gp, maxMana, maxHealth, che.getPosition(), che.getTeam());
        this.currentHealth = che.getCurrentHealth();
        this.currentMana = che.getCurrentMana();
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
