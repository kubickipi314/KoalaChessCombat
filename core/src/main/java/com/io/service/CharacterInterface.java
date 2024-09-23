package com.io.service;

import com.io.core.board.BoardPosition;
import com.io.core.character.CharacterEnum;

public interface CharacterInterface {
    BoardPosition getPosition();

    int getCurrentHealth();

    void changeMana(int mana);

    int getTeam();

    int getCurrentMana();

    boolean isDead();

    CharacterEnum getType();
}
