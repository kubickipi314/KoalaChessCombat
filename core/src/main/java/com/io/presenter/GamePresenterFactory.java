package com.io.presenter;

import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;

public class GamePresenterFactory {
    private final GamePresenter gamePresenter;

    public GamePresenterFactory(int rows, int cols) {

        TextureManager tm = new TextureManager();
        SoundManager sm = new SoundManager();
        CoordinatesManager cm = new CoordinatesManager(rows, cols);

        BarsPresenter barsPresenter = new BarsPresenter(tm, sm, cm);
        BoardPresenter boardPresenter = new BoardPresenter(tm, cm);
        ChessPresenter chessPresenter = new ChessPresenter(tm, sm, cm);

        PlayerPresenter player = new PlayerPresenter(tm, sm, cm);
        player.setManaBar(barsPresenter.getManaBar());
        player.setHealthBar(barsPresenter.getHealthBar());

        EnemyPresenter enemy = new EnemyPresenter(tm, sm, cm);

        gamePresenter = new GamePresenter(player, enemy);
        gamePresenter.setBoardPresenter(boardPresenter);
        gamePresenter.setChessPresenter(chessPresenter);
        gamePresenter.setBarsPresenter(barsPresenter);
    }

    public GamePresenter getGamePresenter() {
        return gamePresenter;
    }
}
