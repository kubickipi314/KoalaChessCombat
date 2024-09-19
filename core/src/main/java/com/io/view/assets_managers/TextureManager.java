package com.io.view.assets_managers;

import com.badlogic.gdx.graphics.Texture;
import com.io.core.CharacterType;
import com.io.core.GameResult;
import com.io.core.moves.MoveType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.io.core.CharacterType.*;
import static com.io.core.GameResult.LOSE;
import static com.io.core.GameResult.WIN;
import static com.io.core.moves.MoveType.*;

public class TextureManager {
    private final Texture normalTile;
    private final Texture markedCover;
    private final Texture availableCover;
    private final Map<MoveType, Texture> chess;
    private final Map<MoveType, Texture> selectedChess;
    private final Map<CharacterType, List<Texture>> characters;
    private final Map<CharacterType, List<Texture>> attacks;
    private final Map<GameResult, Texture> result;
    private final Texture barBackground;
    private final Texture heart;
    private final Texture mana;
    private final List<Texture> tourButtons;
    private final List<Texture> digits;
    private final Texture enemyHealth;

    public TextureManager() {
        chess = new EnumMap<>(MoveType.class);
        selectedChess = new EnumMap<>(MoveType.class);
        characters = new EnumMap<>(CharacterType.class);
        attacks = new EnumMap<>(CharacterType.class);
        result = new EnumMap<>(GameResult.class);

        normalTile = new Texture("textures/tiles/tile.png");
        markedCover = new Texture("textures/tiles/marked_cover.png");
        availableCover = new Texture("textures/tiles/available_cover.png");

        List<Texture> player = new ArrayList<>();
        player.add(new Texture("textures/characters/koala_0.png"));
        player.add(new Texture("textures/characters/koala_1.png"));
        player.add(new Texture("textures/characters/koala_2.png"));

        List<Texture> linux = new ArrayList<>();
        linux.add(new Texture("textures/characters/linux_0.png"));
        linux.add(new Texture("textures/characters/linux_1.png"));
        linux.add(new Texture("textures/characters/linux_2.png"));

        List<Texture> minix = new ArrayList<>();
        minix.add(new Texture("textures/characters/minix_0.png"));
        minix.add(new Texture("textures/characters/minix_1.png"));
        minix.add(new Texture("textures/characters/minix_2.png"));

        characters.put(PLAYER, player);
        characters.put(MINIX, minix);
        characters.put(LINUX, linux);

        List<Texture> shuriken = new ArrayList<>();
        shuriken.add(new Texture("textures/attacks/shuriken_0.png"));
        shuriken.add(new Texture("textures/attacks/shuriken_1.png"));

        List<Texture> fireball = new ArrayList<>();
        fireball.add(new Texture("textures/attacks/fireball_0.png"));
        fireball.add(new Texture("textures/attacks/fireball_1.png"));

        attacks.put(PLAYER, fireball);
        attacks.put(MINIX, shuriken);
        attacks.put(LINUX, shuriken);

        chess.put(KING, new Texture("textures/figures/king.png"));
        chess.put(BISHOP, new Texture("textures/figures/bishop.png"));
        chess.put(KNIGHT, new Texture("textures/figures/knight.png"));
        chess.put(ROOK, new Texture("textures/figures/rook.png"));
        chess.put(QUEEN, new Texture("textures/figures/queen.png"));

        selectedChess.put(KING, new Texture("textures/selected_figures/king.png"));
        selectedChess.put(BISHOP, new Texture("textures/selected_figures/bishop.png"));
        selectedChess.put(KNIGHT, new Texture("textures/selected_figures/knight.png"));
        selectedChess.put(ROOK, new Texture("textures/selected_figures/rook.png"));
        selectedChess.put(QUEEN, new Texture("textures/selected_figures/queen.png"));

        barBackground = new Texture("textures/bars/bar_background.png");
        heart = new Texture("textures/bars/heart.png");
        mana = new Texture("textures/bars/mana.png");

        tourButtons = new ArrayList<>();
        tourButtons.add(new Texture("textures/buttons/tour_button_0.png"));
        tourButtons.add(new Texture("textures/buttons/tour_button_1.png"));
        tourButtons.add(new Texture("textures/buttons/tour_button_2.png"));
        tourButtons.add(new Texture("textures/buttons/tour_button_3.png"));

        digits = new ArrayList<>();
        digits.add(new Texture("textures/digits/zero.png"));
        digits.add(new Texture("textures/digits/one.png"));
        digits.add(new Texture("textures/digits/two.png"));
        digits.add(new Texture("textures/digits/three.png"));
        digits.add(new Texture("textures/digits/four.png"));
        digits.add(new Texture("textures/digits/five.png"));
        digits.add(new Texture("textures/digits/six.png"));
        digits.add(new Texture("textures/digits/seven.png"));
        digits.add(new Texture("textures/digits/eight.png"));
        digits.add(new Texture("textures/digits/nine.png"));

        enemyHealth = new Texture("textures/bars/enemy_health.png");

        result.put(WIN, new Texture("textures/buttons/win_result.png"));
        result.put(LOSE, new Texture("textures/buttons/lose_result.png"));

    }

    public Texture getCharacter(CharacterType type, int stateNumber) {
        return characters.get(type).get(stateNumber);
    }

    public Texture getAttack(CharacterType type, int stateNumber) {
        return attacks.get(type).get(stateNumber);
    }

    public Texture getChess(MoveType move) {
        return chess.get(move);
    }

    public Texture getSelectedChess(MoveType move) {
        return selectedChess.get(move);
    }

    public Texture getNormalTile() {
        return normalTile;
    }

    public Texture getMarkedCover() {
        return markedCover;
    }

    public Texture getAvailableCover() {
        return availableCover;
    }

    public Texture getBarBackground() {
        return barBackground;
    }

    public Texture getHeart() {
        return heart;
    }

    public Texture getMana() {
        return mana;
    }

    public List<Texture> getTourButtons() {
        return tourButtons;
    }

    public Texture getEnemyHealth() {
        return enemyHealth;
    }

    public Texture getDigit(int number) {
        return digits.get(number);
    }

    public Texture getResult(GameResult gameResult) {
        return result.get(gameResult);
    }
}
