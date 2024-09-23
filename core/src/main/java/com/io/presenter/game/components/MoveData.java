package com.io.presenter.game.components;

import com.io.core.moves.MoveType;

public record MoveData(MoveType type, int cost, int damage) {
}
