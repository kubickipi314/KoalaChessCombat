package com.io.viewmodel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.PlayerView;
import com.io.view.TextureManager;

import java.util.ResourceBundle;

public class PlayerViewModel {
    private PlayerView playerView;

    public PlayerViewModel(TextureManager tm, Vector2 position, float size){
        playerView = new PlayerView(tm, position, size);
    }

    public void draw(SpriteBatch batch) {

    }
}
