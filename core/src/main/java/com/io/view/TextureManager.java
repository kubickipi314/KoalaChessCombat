package com.io.view;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class TextureManager {
    private Texture normalTile;
    private Texture markedCover;
    private Texture availableCover;
    private Texture player;

    private Texture chessTile;
    private List<Texture> chessTextures;

    public TextureManager(){
        chessTextures = new ArrayList<>();
    }
    public void load() {
        normalTile = new Texture("textures/tiles/tile.png");
        markedCover = new Texture("textures/tiles/marked_cover.png");
        availableCover = new Texture("textures/tiles/available_cover.png");

        player = new Texture("textures/characters/minix.png");

        chessTile = new Texture("textures/figures/chess_tile.png");
        chessTextures.add(new Texture("textures/figures/king.png"));
        chessTextures.add(new Texture("textures/figures/knight.png"));
        chessTextures.add(new Texture("textures/figures/bishop.png"));
        chessTextures.add(new Texture("textures/figures/rock.png"));
        chessTextures.add(new Texture("textures/figures/queen.png"));
    }


    public Texture getPlayer() {
        return player;
    }

    public Texture[] getChessArray(){
        return chessTextures.toArray(new Texture[0]);
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

    public void dispose() {
    }
}
