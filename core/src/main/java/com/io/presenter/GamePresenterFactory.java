package com.io.presenter;

import com.io.view.SoundManager;
import com.io.view.TextureManager;

public class GamePresenterFactory {
    private static final int NUMBER_OF_CHESS = 5;
    private final GamePresenter gamePresenter;

    public GamePresenterFactory(int rows, int cols) {

        TextureManager tm = new TextureManager();
        SoundManager sm = new SoundManager();
        CoordinatesManager cm = new CoordinatesManager(rows, cols);

        BarsPresenter barsPresenter = new BarsPresenter(tm, sm, cm);
        BoardPresenter boardPresenter = new BoardPresenter(tm, sm, cm);
        ChessPresenter chessPresenter = new ChessPresenter(tm, sm, cm);

        PlayerPresenter player = new PlayerPresenter(tm, sm, cm, barsPresenter);

        gamePresenter = new GamePresenter(player, tm, sm);
        gamePresenter.setBoardPresenter(boardPresenter);
        gamePresenter.setChessPresenter(chessPresenter);
        gamePresenter.setBarsPresenter(barsPresenter);
    }

    public GamePresenter getGamePresenter() {
        return gamePresenter;
    }
}
