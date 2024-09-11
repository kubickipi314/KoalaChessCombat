package com.io.view.assets_managers;

import com.badlogic.gdx.graphics.Texture;
import com.io.core.moves.MoveType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.io.core.moves.MoveType.*;

public class TextureManager {
    private final Texture normalTile;
    private final Texture markedCover;
    private final Texture availableCover;
    private final Texture monkey;
    private final Map<MoveType, Texture> chess;
    private final Map<MoveType, Texture> selectedChess;
    private final Texture barBackground;
    private final Texture heart;
    private final Texture mana;
    private final List<Texture> tourButtons;
    private final List<Texture> player;
    private final List<Texture> digits;
    private final Texture enemyHealth;

    public TextureManager() {
        chess = new EnumMap<>(MoveType.class);
        selectedChess = new EnumMap<>(MoveType.class);

        normalTile = new Texture("textures/tiles/tile.png");
        markedCover = new Texture("textures/tiles/marked_cover.png");
        availableCover = new Texture("textures/tiles/available_cover.png");

        player = new ArrayList<>();
        player.add(new Texture("textures/characters/koala_0.png"));
        player.add(new Texture("textures/characters/koala_1.png"));
        player.add(new Texture("textures/characters/koala_2.png"));
        monkey = new Texture("textures/characters/minix.png");

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

    }


    public Texture getPlayer(int stateNumber) {
        return player.get(stateNumber);
    }

    public Texture getEnemy() {
        return monkey;
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
}
