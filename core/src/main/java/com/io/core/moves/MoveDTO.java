package com.io.core.moves;

import com.io.core.board.BoardPosition;
import com.io.service.game.CharacterInterface;

public record MoveDTO(Move move, BoardPosition boardPosition, CharacterInterface character) {
}
