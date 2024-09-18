package com.io.core.character;

import com.io.CONST;
import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.core.moves.MoveDTO;
import com.io.presenter.GamePresenter;
import com.io.service.GameService;

import java.util.List;

public class Player extends Character {
    static int maxMana = CONST.MAX_PLAYER_MANA, maxHealth = CONST.MAX_PLAYER_HEALTH;
    private final List<Move> moves;


    public Player(GameService gs, GamePresenter gp, BoardPosition position, List<Move> moves) {
        super(gs, gp, maxMana, maxHealth, position, 0);
        this.moves = moves;
    }

    public Player(GameService gs, GamePresenter gp, BoardPosition position, List<Move> moves, int currentHealth, int currentMana) {
        super(gs, gp, maxMana, maxHealth, position, 0, currentHealth, currentMana);
        this.moves = moves;
    }

    public boolean makeNextMove(BoardPosition movePosition, int moveIdx) {
        var moveDTO = new MoveDTO(getMove(moveIdx), movePosition, this);
        return gs.tryMakeMove(moveDTO);
    }

    public Move getMove(int idx) {
        return moves.get(idx);
    }

    public List<Move> getMoves() {
        return moves;
    }
}
