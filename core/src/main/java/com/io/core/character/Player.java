package com.io.core.character;

import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.core.moves.MoveDTO;
import com.io.service.TurnService;
import com.io.presenter.GamePresenter;

import java.util.List;

public class Player extends Character {
    static int maxMana = 10, maxHealth = 5;

    GamePresenter gvm;

    public Player(TurnService ts, GamePresenter gvm, BoardPosition position, List<Move> moves) {
        super(ts, maxMana, maxHealth, position, 0, moves);

        this.gvm = gvm;
    }

    @Override
    public void startTurn() {
        gvm.startTurn();
    }

    public boolean PlayMove(BoardPosition movePosition, int moveIdx) {
        var moveDTO = new MoveDTO(getMove(moveIdx), movePosition, this);
        return ts.tryMakeMove(moveDTO);
    }
}
