package com.io.core.board;

import com.io.core.character.Character;
import com.io.core.moves.MoveDTO;

public record BoardMoveChange(
    boolean success,
    boolean hasMoved,
    boolean hasAttacked,
    MoveDTO move,
    Character attacked
) {
    static BoardMoveChange SUCCESS(
        boolean hasMoved,
        boolean hasAttacked,
        MoveDTO move,
        Character attacked
    ) {
        return new BoardMoveChange(true, hasMoved, hasAttacked, move, attacked);
    }

    static BoardMoveChange FAIL() {
        return new BoardMoveChange(false, false, false, null, null);
    }
}
