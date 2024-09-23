package com.io.core.board;

import com.io.core.moves.MoveDTO;
import com.io.service.game.CharacterInterface;

public record BoardMoveChange(
        boolean success,
        boolean hasMoved,
        boolean hasAttacked,
        MoveDTO move,
        CharacterInterface attacked
) {
    static BoardMoveChange SUCCESS(
            boolean hasMoved,
            boolean hasAttacked,
            MoveDTO move,
            CharacterInterface attacked
    ) {
        return new BoardMoveChange(true, hasMoved, hasAttacked, move, attacked);
    }

    static BoardMoveChange FAIL() {
        return new BoardMoveChange(false, false, false, null, null);
    }
}
