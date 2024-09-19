package com.io.menu.view;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class MenuTextureManager {
    private final List<Texture> quitButton;
    private final List<Texture> playButton;
    private final List<Texture> backButton;
    private final List<Texture> player;
    private final List<Texture> levels;
    public MenuTextureManager() {
        quitButton = new ArrayList<>();
        quitButton.add(new Texture("textures/menu/quit_button_0.png"));
        quitButton.add(new Texture("textures/menu/quit_button_1.png"));

        playButton = new ArrayList<>();
        playButton.add(new Texture("textures/menu/play_button_0.png"));
        playButton.add(new Texture("textures/menu/play_button_1.png"));

        backButton = new ArrayList<>();
        backButton.add(new Texture("textures/menu/back_button_0.png"));
        backButton.add(new Texture("textures/menu/back_button_1.png"));

        player = new ArrayList<>();
        player.add(new Texture("textures/characters/koala_0.png"));
        player.add(new Texture("textures/characters/koala_2.png"));

        levels = new ArrayList<>();
        levels.add(new Texture("textures/menu/level_0.png"));
        levels.add(new Texture("textures/menu/level_1.png"));
    }

    public List<Texture> getQuitButton() {
        return quitButton;
    }
    public List<Texture> getPlayButton() {
        return playButton;
    }

    public List<Texture> getPlayer() {
        return player;
    }

    public List<Texture> getBackButton() {
        return backButton;
    }

    public List<Texture> getLevel() {
        return levels;
    }
}
