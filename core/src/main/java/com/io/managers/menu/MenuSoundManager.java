package com.io.managers.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuSoundManager {
    private final List<Sound> selection;

    public MenuSoundManager() {
        selection = new ArrayList<>();
        selection.add(Gdx.audio.newSound(Gdx.files.internal("sounds/select_1.mp3")));
        selection.add(Gdx.audio.newSound(Gdx.files.internal("sounds/select_2.mp3")));
        selection.add(Gdx.audio.newSound(Gdx.files.internal("sounds/select_3.mp3")));
    }

    public void playSelectSound() {
        Random random = new Random();
        selection.get(random.nextInt(3)).play();
    }
}
