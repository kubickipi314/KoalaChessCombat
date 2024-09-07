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
    private int chessNumber = 0;
    private float chessBoardX;
    private float chessBoardY;
    private float tileSize;
    private List<Move> moves;
    private final SoundManager sm;
    private final CoordinatesManager cm;
    private final BoardPresenter boardPresenter;
    private final TextureManager tm;
    private final GamePresenter gp;
    private int selectedMove = -1;


    public ChessPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPresenter boardPresenter, GamePresenter gp) {
        chessBoard = new ChessTileView[chessNumber];
        this.sm = sm;
        this.cm = cm;
        this.tm = tm;
        this.gp = gp;
        this.boardPresenter = boardPresenter;
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
        for (int tile = 0; tile < chessBoard.length; tile++) {
            if (chessBoard[tile].contains(mousePosition)) {
                return tile;
            }
        }
        throw new Error("mousePosition outside any tile but inside chessBoard");
    }

    public void setMoves(List<Move> moves) {
        if (moves.equals(this.moves)) return;
        this.moves = moves;

        chessNumber = moves.size();
        cm.setChessNumber(moves.size());

        chessBoard = new ChessTileView[chessNumber];
        chessBoardX = cm.getChessBoardX();
        chessBoardY = cm.getChessBoardY();
        tileSize = cm.getTileSize();

        for (int number = 0; number < chessBoard.length; number++) {
            float x = chessBoardX + number * tileSize;
            Vector2 position = new Vector2(x, chessBoardY);
            chessBoard[number] = new ChessTileView(tm.getChessTexture(moves.get(number).getType()),
                    tm.getSelectedTexture(moves.get(number).getType()), position, tileSize);
        }
    }

    private boolean isMouseInChessBoard(Vector2 mousePosition) {
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;
        return mouseX >= chessBoardX && mouseY >= chessBoardY && mouseX <= chessBoardX + tileSize * chessNumber && mouseY <= chessBoardY + 2 * tileSize;
    }

    private void changeChessPiece(Vector2 mousePosition) {
        for (int i = 0; i < chessBoard.length; i++) {
            if (chessBoard[i].contains(mousePosition)) {
                for (int j = 0; j < chessNumber; j++) {
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
        for (int j = 0; j < chessNumber; j++) {
            chessBoard[j].unselect();
        }
        System.out.println("selected " + i);
        System.out.println(chessBoard[i]);
        chessBoard[i].select();
    }

    public void render(SpriteBatch batch) {
        for (int number = 0; number < chessBoard.length; number++) {
            chessBoard[number].draw(batch);
        }
    }
}
