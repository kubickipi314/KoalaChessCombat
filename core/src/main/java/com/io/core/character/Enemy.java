package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.MoveDTO;
import com.io.presenter.GamePresenter;
import com.io.service.GameService;

public abstract class Enemy extends Character {
    public Enemy(GameService gs, GamePresenter gp, int maxMana, int maxHealth, BoardPosition position) {
        super(gs, gp, maxMana, maxHealth, position, 1);
    }

    @Override
    public void startTurn() {
        while (true) {
            Board board = gs.getBoardSnapshot();
            MoveDTO moveDTO = getNextMove(board);
            // if enemy wants to end turn
            if (moveDTO == null) break;

            if (!gs.tryMakeMove(moveDTO)) {
                System.err.println("Enemy failed to make a valid move\t" + moveDTO.boardPosition() + ' ' + moveDTO.character().getPosition());
                break;
            }
        }
        gs.endTurn();
    }

    abstract MoveDTO getNextMove(Board board);
}
