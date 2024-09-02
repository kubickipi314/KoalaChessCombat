package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.GameResult;
import com.io.view.*;

public class GamePresenter {
    private final SpriteBatch batch;
    private BoardPresenter boardPresenter;
    private ChessPresenter chessPresenter;
    private BarsPresenter barsPresenter;
    private TourButton tourButton;
    private final PlayerPresenter player;

    protected final float windowHeight;


    public GamePresenter(PlayerPresenter player, TextureManager tm, SoundManager sm) {
        batch = new SpriteBatch();
        this.player = player;
        windowHeight = Gdx.graphics.getHeight();
    }
    public void setBoardPresenter(BoardPresenter boardPresenter){
        this.boardPresenter = boardPresenter;
        boardPresenter.setPlayer(this.player);
    }
    public void setChessPresenter(ChessPresenter chessPresenter){
        this.chessPresenter = chessPresenter;
    }
    public void setBarsPresenter(BarsPresenter barsPresenter){
        this.barsPresenter = barsPresenter;
        tourButton = barsPresenter.getTourButton();
    }

    public void update() {

        if (player.isMoving()) {
            player.updatePlayerPosition();
        } else {
            Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            float mouseX = mouseWorldCoords.x;
            float mouseY = (windowHeight - mouseWorldCoords.y);
            Vector2 mousePosition = new Vector2(mouseX, mouseY);

            boardPresenter.handleInput(mousePosition);
            chessPresenter.handleInput(mousePosition);
            handleTourButton(mousePosition);
        }
    }

    public void render() {
        batch.begin();
        boardPresenter.render(batch);

        player.render(batch);

        chessPresenter.render(batch);

        barsPresenter.render(batch);
        tourButton.draw(batch);
        batch.end();
    }

    private void handleTourButton(Vector2 mousePosition) {
        if (tourButton.contains(mousePosition)) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                player.increaseMana(2);
                player.decreaseHealth(1);
                //sm.playSwordSound();
            }
        }
    }

    public void endGame(GameResult gameResult) {
    }

    public void startTurn() {
    }
}
