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
    static int maxMana = 1, maxHealth = 4;

    public RangeEnemy(BoardPosition position, Board board) {
        super(maxMana, maxHealth, position, board);
        this.type = CharacterEnum.RangeEnemy;
    }

    public RangeEnemy(CharacterEntity che, Board board) {
        super(maxMana, maxHealth, che, board);
        this.type = CharacterEnum.RangeEnemy;
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
        BoardPosition playerPosition = playerTeamPosition.get(CONST.PLAYER_TEAM);

        System.out.println("long");
        var longRangeMove = new LongRangeMove(1, 1, 3, board);
        var kingMove = new KingMove(1, 0, board);
        if (longRangeMove.isMoveValid(this, playerPosition)) {
            return new MoveDTO(longRangeMove, playerPosition, this);
        } else {
            var movePositionArr = new ArrayList<>(kingMove.getAccessibleCells(position));
            Collections.shuffle(movePositionArr);

            movePositionArr.sort((l, r) -> -(kingDistance(playerPosition, l) - kingDistance(playerPosition, r)));
            for (var newPosition : movePositionArr) {
                if (kingMove.isMoveValid(this, newPosition)) {
                    return new MoveDTO(kingMove, newPosition, this);
                }
            }
            return null;
        }
    }
}
