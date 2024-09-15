package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.moves.Move;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.tiles.ChessTileView;

import java.util.List;


public class ChessPresenter {
    private final SoundManager sm;
    private final CoordinatesManager cm;
    private final TextureManager tm;

    private ChessTileView[] chessBoard;

    private int actualTile;

    private int numberOfMoves;
    private float chessBoardX;
    private float chessBoardY;
    private float tileSize;
    private List<Move> moves;

    private int selectedMove;

    public ChessPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, List<Move> moves) {
        this.sm = sm;
        this.cm = cm;
        this.tm = tm;

        setMoves(moves);
        selectMove(0);
    }

    public void handleInput(Vector2 mousePosition) {
        chessBoard[actualTile].unmark();

        if (isMouseInChessBoard(mousePosition)) {
            getActualTile(mousePosition);
            chessBoard[actualTile].mark();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                selectMove(actualTile);
                sm.playSelectSound();
            }
        }
    }

    private void getActualTile(Vector2 mousePosition) {
        for (int tile = 0; tile < numberOfMoves; tile++) {
            if (chessBoard[tile].contains(mousePosition)) {
                actualTile = tile;
                return;
            }
        }
        throw new Error("mousePosition outside any tile but inside chessBoard");
    }

    private void setMoves(List<Move> moves) {
        if (moves.equals(this.moves)) return;

        this.moves = moves;
        numberOfMoves = moves.size();
        cm.setChessNumber(numberOfMoves);

        chessBoard = new ChessTileView[numberOfMoves];
        chessBoardX = cm.getChessBoardX();
        chessBoardY = cm.getChessBoardY();
        tileSize = cm.getTileSize();

        for (int number = 0; number < numberOfMoves; number++) {
            float x = chessBoardX + number * tileSize;
            Vector2 position = new Vector2(x, chessBoardY);
            var type = moves.get(number).getType();
            chessBoard[number] = new ChessTileView(tm.getChessTexture(type),
                tm.getSelectedTexture(type), position, tileSize);
        }
    }

    private boolean isMouseInChessBoard(Vector2 mousePosition) {
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;
        return mouseX >= chessBoardX && mouseY >= chessBoardY && mouseX <= chessBoardX + tileSize * numberOfMoves && mouseY <= chessBoardY + 2 * tileSize;
    }

    public void render(SpriteBatch batch) {
        for (ChessTileView chessTileView : chessBoard) {
            chessTileView.draw(batch);
        }
    }

    public int getSelectedMove() {
        return selectedMove;
    }

    void selectMove(int i) {
        selectedMove = i;
        for (int j = 0; j < numberOfMoves; j++) {
            chessBoard[j].unselect();
        }
        chessBoard[i].select();
    }
}
