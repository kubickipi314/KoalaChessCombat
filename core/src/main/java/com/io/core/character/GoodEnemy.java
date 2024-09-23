package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.MoveDTO;
import com.io.core.moves.ShortRangeMove;
import com.io.db.entity.CharacterEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.io.core.moves.MovesUtils.kingDistance;

public class GoodEnemy extends Enemy {
    static int maxMana = 1, maxHealth = 6;

    public GoodEnemy(BoardPosition position, Board board) {
        super(maxMana, maxHealth, position, board);
        this.type = CharacterEnum.GoodEnemy;
    }

    public GoodEnemy(CharacterEntity che, Board board) {
        super(maxMana, maxHealth, che, board);
        this.type = CharacterEnum.GoodEnemy;
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
        var shortRangeMove = new ShortRangeMove(1, 1, 2, board);
        var kingMove = new KingMove(1, 1, board);
        if (shortRangeMove.isMoveValid(this, playerPosition)) {
            return new MoveDTO(shortRangeMove, playerPosition, this);
        } else {
            var movePositionArr = new ArrayList<>(kingMove.getAccessibleCells(position));
            Collections.shuffle(movePositionArr);

            var curDistance = kingDistance(playerPosition, position);
            for (var newPosition : movePositionArr) {
                if (kingDistance(playerPosition, newPosition) < curDistance && kingMove.isMoveValid(this, newPosition)) {
                    return new MoveDTO(kingMove, newPosition, this);
                }
            }
            return null;
        }
    }
}

