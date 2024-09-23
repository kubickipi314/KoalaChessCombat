package com.io.service.game;

import com.io.core.moves.MoveDTO;

public interface EnemyInterface extends CharacterInterface {
    MoveDTO makeNextMove();
}
