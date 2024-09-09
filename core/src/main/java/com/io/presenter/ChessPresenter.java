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
    private ChessTileView[] chessBoard;
    private int numberOfMoves = 0;
    private float chessBoardX;
    private float chessBoardY;
    private float tileSize;
    private List<Move> moves;
    private final SoundManager sm;
    private final CoordinatesManager cm;
    private final TextureManager tm;
    private final GamePresenter gp;
    private int selectedMove = -1;


    public ChessPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, GamePresenter gp) {
        chessBoard = new ChessTileView[numberOfMoves];
        this.sm = sm;
        this.cm = cm;
        this.tm = tm;
        this.gp = gp;
    }


    public void handleInput(Vector2 mousePosition) {
        if (isMouseInChessBoard(mousePosition)) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

                gp.choseMove(getActualTile(mousePosition));
                sm.playSelectSound();
            }
        }
    }

    private int getActualTile(Vector2 mousePosition) {
        for (int tile = 0; tile < numberOfMoves; tile++) {
            if (chessBoard[tile].contains(mousePosition)) {
                return tile;
            }
        }
        throw new Error("mousePosition outside any tile but inside chessBoard");
    }

    public void setMoves(List<Move> moves) {
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

    private void changeChessPiece(Vector2 mousePosition) {
        for (int i = 0; i < numberOfMoves; i++) {
            if (chessBoard[i].contains(mousePosition)) {
                for (int j = 0; j < numberOfMoves; j++) {
                    chessBoard[j].unselect();
                }
                chessBoard[i].select();
                sm.playSelectSound();
                break;
            }
        }
    }

    public void selectMove(int i) {
        if (i == selectedMove) return;
        selectedMove = i;
        for (int j = 0; j < numberOfMoves; j++) {
            chessBoard[j].unselect();
        }
        System.out.println("selected " + i);
        System.out.println(chessBoard[i]);
        chessBoard[i].select();
    }

    public void render(SpriteBatch batch) {
        for (int number = 0; number < numberOfMoves; number++) {
            chessBoard[number].draw(batch);
        }
    }
}
