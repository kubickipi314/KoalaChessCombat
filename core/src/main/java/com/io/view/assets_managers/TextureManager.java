package com.io.view.assets_managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class TextureManager {
    private final Texture normalTile;
    private final Texture markedCover;
    private final Texture availableCover;
    private final Texture player;
    private final Texture monkey;
    private final List<Texture> chess;
    private final List<Texture> selectedChess;
    private final Texture barBackground;
    private final Texture heart;
    private final Texture mana;
    private final Texture tourButton;
    private final Texture enemyHealth;

    public TextureManager() {
        chess = new ArrayList<>();
        selectedChess = new ArrayList<>();

        normalTile = new Texture("textures/tiles/tile.png");
        markedCover = new Texture("textures/tiles/marked_cover.png");
        availableCover = new Texture("textures/tiles/available_cover.png");

        player = new Texture("textures/characters/koala.png");
        monkey = new Texture("textures/characters/minix.png");

        chess.add(new Texture("textures/figures/king.png"));
        chess.add(new Texture("textures/figures/bishop.png"));
        chess.add(new Texture("textures/figures/knight.png"));
        chess.add(new Texture("textures/figures/rock.png"));
        chess.add(new Texture("textures/figures/queen.png"));

        selectedChess.add(new Texture("textures/selected_figures/king.png"));
        selectedChess.add(new Texture("textures/selected_figures/bishop.png"));
        selectedChess.add(new Texture("textures/selected_figures/knight.png"));
        selectedChess.add(new Texture("textures/selected_figures/rock.png"));
        selectedChess.add(new Texture("textures/selected_figures/queen.png"));

        barBackground = new Texture("textures/bars/bar_background.png");
        heart = new Texture("textures/bars/heart.png");
        mana = new Texture("textures/bars/mana.png");
        tourButton = new Texture("textures/bars/next_tour_button.png");

        enemyHealth = new Texture("textures/bars/enemy_health.png");

    }


    public Texture getPlayer() {
        return player;
    }

    public Texture getEnemy() {
        return monkey;
    }

    public Texture[] getChessArray() {
        return chess.toArray(new Texture[0]);
    }

    public Texture[] getSelectedPieces() {
        return selectedChess.toArray(new Texture[0]);
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

    public Texture getTourButton() {
        return tourButton;
    }

    public Texture getEnemyHealth() {
        return enemyHealth;
    }

    public void dispose() {
    }

}
