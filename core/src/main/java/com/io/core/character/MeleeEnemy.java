package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.KnightMove;
import com.io.core.moves.MoveDTO;
import com.io.presenter.GamePresenter;
import com.io.service.GameService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MeleeEnemy extends Enemy {
    static int maxMana = 2, maxHealth = 5;

    public MeleeEnemy(GameService gs, GamePresenter gp, BoardPosition position) {
        super(gs, gp, maxMana, maxHealth, position);
    }

    public MeleeEnemy(GameService gs, GamePresenter gp, BoardPosition position, int currentHealth, int currentMana) {
        super(gs, gp, maxMana, maxHealth, position, currentHealth, currentMana);
    }

    @Override
    public boolean makeNextMove() {
        if (currentMana <= 0)
            return false;

        Board board = gs.getBoardSnapshot();

        List<BoardPosition> playerTeamPosition = board.getTeamPosition(0);
        if (playerTeamPosition.isEmpty()) {
            System.err.println("No player found on the board");
            return false;
        }
        BoardPosition playerPosition = playerTeamPosition.get(0);

        var knightMove = new KnightMove(1, 1);
        var move = new KingMove(1, 1);
        if (knightMove.isMoveValid(this, playerPosition, board)) {
            return gs.tryMakeMove(new MoveDTO(knightMove, playerPosition, this));
        } else {
            var movePositionArr = Arrays.asList(new BoardPosition[]{
                    new BoardPosition(position.x() - 1, position.y()),
                    new BoardPosition(position.x() + 1, position.y()),
                    new BoardPosition(position.x(), position.y() - 1),
                    new BoardPosition(position.x(), position.y() + 1)
            });
            Collections.shuffle(movePositionArr);

            var curDistance = distance(playerPosition, position);
            for (var newPosition : movePositionArr) {
                if (distance(playerPosition, newPosition) < curDistance && move.isMoveValid(this, newPosition, board)) {
                    return gs.tryMakeMove(new MoveDTO(move, newPosition, this));
                }
            }
            return false;
        }
    }

    private int distance(BoardPosition p, BoardPosition q) {
        return Math.abs(p.x() - q.x()) + Math.abs(p.y() - q.y());
    }
}
