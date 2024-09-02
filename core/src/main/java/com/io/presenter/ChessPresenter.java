package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.tiles.ChessTileView;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;


public class ChessPresenter {
    private final ChessTileView[] chessBoard;
    private final int NUMBER_OF_CHESS = 5;
    private final float chessBoardX;
    private final float chessBoardY;
    private final float tileSize;

    SoundManager sm;


    public ChessPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm) {
        chessBoard = new ChessTileView[NUMBER_OF_CHESS];
        chessBoardX = cm.getChessBoardX();
        chessBoardY = cm.getChessBoardY();
        tileSize = cm.getTileSize();
        this.sm = sm;

        Texture[] chessPieces = tm.getChessArray();
        Texture[] selectedPieces = tm.getSelectedPieces();

        for (int number = 0; number < NUMBER_OF_CHESS; number++) {
            float x = chessBoardX + number * tileSize;
            Vector2 position = new Vector2(x, chessBoardY);
            chessBoard[number] = new ChessTileView(chessPieces[number], selectedPieces[number], position, tileSize);
        }
    }

    public void handleInput(Vector2 mousePosition){
        if (isMouseInChessBoard(mousePosition)) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                changeChessPiece(mousePosition);
            }
        }
    }

    private boolean isMouseInChessBoard(Vector2 mousePosition) {
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;
        return mouseX >= chessBoardX && mouseY >= chessBoardY && mouseX <= chessBoardX + tileSize * NUMBER_OF_CHESS && mouseY <= chessBoardY + 2 * tileSize;
    }

    private void changeChessPiece(Vector2 mousePosition){
        for (int i = 0; i < NUMBER_OF_CHESS; i++) {
            if (chessBoard[i].contains(mousePosition)) {
                for (int j = 0; j < NUMBER_OF_CHESS; j++) {
                    chessBoard[j].unselect();
                }
                chessBoard[i].select();
                sm.playSelectSound();
                break;
            }
        }
    }

    public void render(SpriteBatch batch){
        for (int number = 0; number < NUMBER_OF_CHESS; number++) {
            chessBoard[number].draw(batch);
        }
    }
}
