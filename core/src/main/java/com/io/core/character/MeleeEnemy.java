package com.io.core.character;

import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.KnightMove;
import com.io.core.moves.MoveDTO;
import com.io.db.entity.CharacterEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MeleeEnemy extends Enemy {
    static int maxMana = 2, maxHealth = 5;

    public MeleeEnemy(BoardPosition position, Board board) {
        super(maxMana, maxHealth, position, board);
    }

    public MeleeEnemy(CharacterEntity che, Board board) {
        super(maxMana, maxHealth, che, board);
    }

    @Override
    public MoveDTO makeNextMove() {
        if (currentMana <= 0)
            return null;

        List<BoardPosition> playerTeamPosition = board.getTeamPosition(0);
        if (playerTeamPosition.isEmpty()) {
            System.err.println("No player found on the board");
            return null;
        }
        BoardPosition playerPosition = playerTeamPosition.get(0);

        var knightMove = new KnightMove(1, 1, board);
        var move = new KingMove(1, 1, board);
        if (knightMove.isMoveValid(this, playerPosition)) {
            return new MoveDTO(knightMove, playerPosition, this);
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
                if (distance(playerPosition, newPosition) < curDistance && move.isMoveValid(this, newPosition)) {
                    return new MoveDTO(move, newPosition, this);
                }
            }
            return null;
        }
    }

    private int distance(BoardPosition p, BoardPosition q) {
        return Math.abs(p.x() - q.x()) + Math.abs(p.y() - q.y());
    }
}
