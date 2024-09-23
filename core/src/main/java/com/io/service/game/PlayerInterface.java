package com.io.service.game;

import com.io.core.moves.Move;

import java.util.List;

public interface PlayerInterface extends CharacterInterface {
    void changeMana(int mana);

    List<Move> getMoves();

    Move getMove(int moveNumber);
}
