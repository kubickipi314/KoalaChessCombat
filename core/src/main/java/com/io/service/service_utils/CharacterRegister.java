package com.io.service.service_utils;

import com.io.view.game.characters.CharacterViewType;
import com.io.core.board.BoardPosition;

public record CharacterRegister(
        boolean player,
        int characterId,
        CharacterViewType type,
        BoardPosition position,
        int maxHealth) {
}
