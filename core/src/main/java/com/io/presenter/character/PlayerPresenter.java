package com.io.presenter.character;

import com.badlogic.gdx.math.Vector2;
import com.io.core.CharacterType;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.PlayerView;


public class PlayerPresenter extends CharacterPresenter {

    public PlayerPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition startBoardPosition) {
        super(sm, cm, startBoardPosition);

        Vector2 position = cm.calculatePosition(boardPosition);
        characterView = new PlayerView(tm, position, cm.getTileSize());
        characterType = CharacterType.PLAYER;
    }

    @Override
    public void startAttack(BoardPosition position) {
        movementTime = 0;
        attackTime = 0;
        sm.playSwordSound();
        isAttacking = true;
        attackPosition = cm.calculatePosition(position);
        attackerPosition = cm.calculatePosition(boardPosition);
    }
}

