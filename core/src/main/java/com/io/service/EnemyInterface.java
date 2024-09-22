package com.io.service;

import com.io.core.moves.MoveDTO;

public interface EnemyInterface extends CharacterInterface {
    MoveDTO makeNextMove();
}
