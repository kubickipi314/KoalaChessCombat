package com.io.service;

import com.io.core.board.BoardPosition;

public interface CharacterInterface {
    BoardPosition getPosition();

    int getCurrentHealth();

    int getTeam();

    int getCurrentMana();

}
