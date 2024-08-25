package com.io.core.moves;

import com.io.core.board.BoardPosition;
import com.io.core.character.Character;

public record MoveDTO(Move move, BoardPosition boardPosition, Character character) {
}
