package com.io.presenter.game.character;

import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.presenter.game.CoordinatesManager;
import com.io.view.game.SoundManager;
import com.io.view.game.TextureManager;
import com.io.view.game.characters.CharacterViewType;
import com.io.view.game.characters.PlayerView;


public class PlayerPresenter extends CharacterPresenter {

    public PlayerPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition startBoardPosition, CharacterViewType type) {
        super(sm, cm, startBoardPosition, type);

        Vector2 position = cm.calculatePosition(boardPosition);
        characterView = new PlayerView(tm, position, cm.getTileSize(), type);
    }

}

