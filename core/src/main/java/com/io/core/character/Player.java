package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.db.entity.CharacterEntity;
import com.io.service.PlayerInterface;

import java.util.List;

public class Player extends Character implements PlayerInterface {
    static int maxMana = CONST.MAX_PLAYER_MANA, maxHealth = CONST.MAX_PLAYER_HEALTH;
    private final List<Move> moves;

    public Player(BoardPosition position, List<Move> moves, Board board) {
        super(maxMana, maxHealth, position, 0, board);
        this.moves = moves;
        this.type = CharacterEnum.Player;
    }

    public Player(CharacterEntity che, List<Move> moves, Board board) {
        super(maxMana, maxHealth, che, board);
        this.moves = moves;
        this.type = CharacterEnum.Player;
    }

    public Move getMove(int idx) {
        return moves.get(idx);
    }

    public List<Move> getMoves() {
        return moves;
    }
}
