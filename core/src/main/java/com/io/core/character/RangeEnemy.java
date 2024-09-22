package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.LongRangeMove;
import com.io.core.moves.MoveDTO;
import com.io.db.entity.CharacterEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.io.core.moves.MovesUtils.kingDistance;

public class RangeEnemy extends Enemy {
    static int maxMana = 1, maxHealth = 5;

    public RangeEnemy(BoardPosition position) {
        super(maxMana, maxHealth, position);
    }

    public RangeEnemy(CharacterEntity che) {
        super(maxMana, maxHealth, che);
    }

    @Override
    public MoveDTO makeNextMove(Board board) {
        if (currentMana <= 0)
            return null;

        List<BoardPosition> playerTeamPosition = board.getTeamPosition(0);
        if (playerTeamPosition.isEmpty()) {
            System.err.println("No player found on the board");
            return null;
        }
        BoardPosition playerPosition = playerTeamPosition.get(CONST.PLAYER_TEAM);

        System.out.println("long");
        var longRangeMove = new LongRangeMove(1, 2, 3);
        var kingMove = new KingMove(1, 0);
        if (longRangeMove.isMoveValid(this, playerPosition, board)) {
            return new MoveDTO(longRangeMove, playerPosition, this);
        } else {
            var movePositionArr = new ArrayList<>(List.of(
                    new BoardPosition(position.x() - 1, position.y()),
                    new BoardPosition(position.x() + 1, position.y()),
                    new BoardPosition(position.x(), position.y() - 1),
                    new BoardPosition(position.x(), position.y() + 1)
            ));
            Collections.shuffle(movePositionArr);

            movePositionArr.sort((l, r) -> -(kingDistance(playerPosition, l) - kingDistance(playerPosition, r)));
            for (var newPosition : movePositionArr) {
                if (kingMove.isMoveValid(this, newPosition, board)) {
                    return new MoveDTO(kingMove, newPosition, this);
                }
            }
            return null;
        }
    }
}
