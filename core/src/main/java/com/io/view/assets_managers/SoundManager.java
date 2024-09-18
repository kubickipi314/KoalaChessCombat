package com.io.view.assets_managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private final Sound move;
    private final Sound select;
    private final Sound sword;
    private final Sound roar;

    public SoundManager() {
        move = Gdx.audio.newSound(Gdx.files.internal("sounds/move_sound_1.mp3"));
        select = Gdx.audio.newSound(Gdx.files.internal("sounds/select_sound_1.mp3"));
        sword = Gdx.audio.newSound(Gdx.files.internal("sounds/sword_sound_1.mp3"));
        roar = Gdx.audio.newSound(Gdx.files.internal("sounds/roar_1.mp3"));
    }


    public void playMoveSound() {
        move.play();
    }

    public void playSelectSound() {
        select.play();
    }

    public void playSwordSound() {
        sword.play();
    }

    public void playRoarSound() {
        roar.play();
    }
}
