package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.MoveDTO;
import com.io.db.entity.CharacterEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.io.core.moves.MovesUtils.kingDistance;

public class MeleeEnemy extends Enemy {
    static int maxMana = 2, maxHealth = 5;

    public MeleeEnemy(BoardPosition position, Board board) {
        super(maxMana, maxHealth, position, board);
        this.type = CharacterEnum.MeleeEnemy;
    }

    public MeleeEnemy(CharacterEntity che, Board board) {
        super(maxMana, maxHealth, che, board);
        this.type = CharacterEnum.MeleeEnemy;
    }

    @Override
    public MoveDTO makeNextMove() {
        if (currentMana <= 0)
            return null;

        List<BoardPosition> playerTeamPosition = board.getTeamPosition(CONST.PLAYER_TEAM);
        if (playerTeamPosition.isEmpty()) {
            System.err.println("No player found on the board");
            return null;
        }
        BoardPosition playerPosition = playerTeamPosition.get(0);

        var move = new KingMove(1, 1, board);
        var movePositionList = new ArrayList<>(move.getAccessibleCells(position));
        Collections.shuffle(movePositionList);

        var curDistance = kingDistance(playerPosition, position);
        for (var newPosition : movePositionList) {
            if (kingDistance(playerPosition, newPosition) < curDistance && move.isMoveValid(this, newPosition)) {
                return new MoveDTO(move, newPosition, this);
            }
        }
        return null;
    }
}
