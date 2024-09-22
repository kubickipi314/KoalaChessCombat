package com.io.core.character;

import com.io.CONST;
import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.db.entity.CharacterEntity;

import java.util.List;

public class Player extends Character {
    static int maxMana = CONST.MAX_PLAYER_MANA, maxHealth = CONST.MAX_PLAYER_HEALTH;
    private final List<Move> moves;

    public Player(BoardPosition position, List<Move> moves) {
        super(maxMana, maxHealth, position, CONST.PLAYER_TEAM);
        this.moves = moves;
    }

    public Player(CharacterEntity che, List<Move> moves) {
        super(maxMana, maxHealth, che);
        this.moves = moves;
    }

    public Move getMove(int idx) {
        return moves.get(idx);
    }

    public List<Move> getMoves() {
        return moves;
    }
}
