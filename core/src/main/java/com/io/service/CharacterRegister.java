package com.io.service;

import com.io.core.CharacterType;
import com.io.core.board.BoardPosition;

public record CharacterRegister(
        int characterId,
        CharacterType type,
        BoardPosition position,
        int maxHealth) {
}
