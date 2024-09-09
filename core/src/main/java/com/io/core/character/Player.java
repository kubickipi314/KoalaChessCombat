package com.io.core.character;

import com.io.CONST;
import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.core.moves.MoveDTO;
import com.io.presenter.GamePresenter;
import com.io.service.TurnService;

import java.util.List;

public class Player extends Character {
    static int maxMana = CONST.MAX_PLAYER_MANA, maxHealth = CONST.MAX_PLAYER_HEALTH;

    GamePresenter gp;

    public Player(TurnService ts, GamePresenter gp, BoardPosition position, List<Move> moves) {
        super(ts, maxMana, maxHealth, position, 0, moves);
        this.gp = gp;

    }

    @Override
    public void startTurn() {
        gp.startTurn();
    }

    public boolean PlayMove(BoardPosition movePosition, int moveIdx) {
        var moveDTO = new MoveDTO(getMove(moveIdx), movePosition, this);
        return ts.tryMakeMove(moveDTO);
    }
}
