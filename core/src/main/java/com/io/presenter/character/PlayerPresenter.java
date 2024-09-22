package com.io.presenter.character;

import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.CharacterViewType;
import com.io.view.characters.PlayerView;


public class PlayerPresenter extends CharacterPresenter {

    public PlayerPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition startBoardPosition, CharacterViewType type) {
        super(sm, cm, startBoardPosition, type);

        Vector2 position = cm.calculatePosition(boardPosition);
        characterView = new PlayerView(tm, position, cm.getTileSize(), type);
    }

}

