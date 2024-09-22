package com.io.service.service_utils;

import com.io.core.board.BoardPosition;

public record MoveResult(
        int characterId,
        boolean hasMoved,
        BoardPosition targetPosition,
        boolean hasAttacked,
        boolean isAttackedDead,
        int attackedId,
        int attackedHealth) {
}
