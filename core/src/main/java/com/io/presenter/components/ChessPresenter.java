package com.io.presenter.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.moves.Move;
import com.io.presenter.CoordinatesManager;
import com.io.presenter.GamePresenter;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.tiles.ChessTileView;

import java.util.List;


public class ChessPresenter {
    private ChessTileView[] chessBoard;

    private final GamePresenter gamePresenter;
    private final SoundManager sm;
    private final CoordinatesManager cm;
    private final TextureManager tm;

    private int actualTile;

    private int numberOfMoves = 0;
    private float chessBoardX;
    private float chessBoardY;
    private float tileSize;

    private List<Move> moves;
    private int selectedMoveNumber = -1;


    public ChessPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, GamePresenter gamePresenter, List<Move> moves) {
        this.sm = sm;
        this.cm = cm;
        this.tm = tm;

        this.gamePresenter = gamePresenter;

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
                gamePresenter.updateAvailableTiles();
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
            Move move = moves.get(number);
            var type = move.getType();
            chessBoard[number] = new ChessTileView(tm.getChess(type),
                    tm.getSelectedChess(type), position, tileSize);

            int damageNumber = move.getDamage();
            int costNumber = move.getCost();
            chessBoard[number].setDamage(tm.getDigit(damageNumber));
            chessBoard[number].setCost(tm.getDigit(costNumber));
        }
    }

    private boolean isMouseInChessBoard(Vector2 mousePosition) {
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;
        return mouseX >= chessBoardX && mouseY >= chessBoardY && mouseX <= chessBoardX + tileSize * numberOfMoves && mouseY <= chessBoardY + 2 * tileSize;
    }

    public void selectMove(int i) {
        if (i == selectedMoveNumber) return;
        selectedMoveNumber = i;
        for (int j = 0; j < numberOfMoves; j++) {
            chessBoard[j].unselect();
        }
        System.out.println("selected " + i);
        System.out.println(chessBoard[i]);
        chessBoard[i].select();
    }

    public void render(SpriteBatch batch) {
        for (ChessTileView chessTileView : chessBoard) {
            chessTileView.draw(batch);
        }
    }

    public Move getSelectedMove() {
        return moves.get(selectedMoveNumber);
    }
}
