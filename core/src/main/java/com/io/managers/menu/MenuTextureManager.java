package com.io.managers.menu;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MenuTextureManager {
    private List<Texture> getTextureList(String name, int count) {
        return IntStream.range(0, count).mapToObj(idx -> new Texture(name + "_" + idx + ".png")).collect(Collectors.toList());
    }

    private final List<Texture> quitButton = getTextureList("textures/menu/quit_button", 2);
    private final List<Texture> playButton = getTextureList("textures/menu/play_button", 2);
    private final List<Texture> backButton = getTextureList("textures/menu/back_button", 2);
    private final List<List<Texture>> levels;
    private final List<Texture> rightArrow = getTextureList("textures/menu/right_arrow", 2);
    private final List<Texture> leftArrow = getTextureList("textures/menu/left_arrow", 2);
    private final List<Texture> player = List.of(new Texture("textures/characters/koala_0.png"),
            new Texture("textures/characters/koala_2.png"));

    public MenuTextureManager() {
        levels = new ArrayList<>();
        levels.add(getTextureList("textures/menu/level1", 2));
        levels.add(getTextureList("textures/menu/level2", 2));
        levels.add(getTextureList("textures/menu/level3", 2));
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

    public List<Texture> getLevel(long levelNumber) {
        return levels.get((int)levelNumber - 1);
    }

    public List<Texture> getRightArrow() {
        return rightArrow;
    }

    public List<Texture> getLeftArrow() {
        return leftArrow;
    }
}
