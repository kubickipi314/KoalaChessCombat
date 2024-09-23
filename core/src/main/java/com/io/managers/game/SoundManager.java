package com.io.managers.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.io.view.game.characters.CharacterViewType;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private final Sound move;
    private final Sound select;
    private final Sound sword;
    private final Map<CharacterViewType, Sound> attacks;

    public SoundManager() {
        attacks = new HashMap<>();
        attacks.put(CharacterViewType.MINIX, Gdx.audio.newSound(Gdx.files.internal("sounds/roar_1.mp3")));
        attacks.put(CharacterViewType.LINUX, Gdx.audio.newSound(Gdx.files.internal("sounds/roar_2.mp3")));
        attacks.put(CharacterViewType.KOALA, Gdx.audio.newSound(Gdx.files.internal("sounds/roar_1.mp3")));
        attacks.put(CharacterViewType.FIREFOX, Gdx.audio.newSound(Gdx.files.internal("sounds/space_shoot_0.mp3")));

        move = Gdx.audio.newSound(Gdx.files.internal("sounds/move_sound_0.mp3"));
        select = Gdx.audio.newSound(Gdx.files.internal("sounds/chess_select_0.mp3"));
        sword = Gdx.audio.newSound(Gdx.files.internal("sounds/sword_sound_0.mp3"));
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


    public void playAttackSound(CharacterViewType characterType) {
        attacks.get(characterType).play();
    }
}
