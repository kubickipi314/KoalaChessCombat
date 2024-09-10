package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.character.Player;
import com.io.service.GameService;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;

public class GamePresenter {
    private final SpriteBatch batch;
    private final BoardPresenter boardPresenter;
    private final ChessPresenter chessPresenter;
    private final BarsPresenter barsPresenter;
    private final ButtonsPresenter buttonsPresenter;
    private final PlayerPresenter player;
    protected final float windowHeight;

    private final GameService gs;

    BoardPosition lastBoardPosition = new BoardPosition(-1, -1);
    int lastChosenMove = -1;


    public GamePresenter(GameService gs) {
        BoardPosition startingPosition = gs.getPlayer().getPosition();

        batch = new SpriteBatch();
        this.gs = gs;
        CoordinatesManager cm = new CoordinatesManager(gs.getRoomHeight(), gs.getRoomWidth());
        TextureManager tm = new TextureManager();
        SoundManager sm = new SoundManager();
        this.player = new PlayerPresenter(tm, sm, cm, startingPosition);

        this.barsPresenter = new BarsPresenter(tm, cm);

        this.boardPresenter = new BoardPresenter(tm, cm, this);
        this.chessPresenter = new ChessPresenter(tm, sm, cm, this);
        this.buttonsPresenter = new ButtonsPresenter(tm, sm, cm, this);

        windowHeight = Gdx.graphics.getHeight();
    }

    public void update() {
        boolean active = false;
        if (player.isMoving()) {
            player.updatePosition();
            active = true;
        }
        if (!active) {
            updateFromModel();

            Vector2 mousePosition = getMousePosition();
            boardPresenter.handleInput(mousePosition);
            chessPresenter.handleInput(mousePosition);
            buttonsPresenter.handleInput(mousePosition);
        }
    }

    private void updateFromModel() {
        // temporary solution presenter might need more information

        Player playerModel = gs.getPlayer();
        int playerX = playerModel.getPosition().x();
        int playerY = playerModel.getPosition().y();
        player.update(playerX, playerY);

        if (!lastBoardPosition.equals(playerModel.getPosition()) || lastChosenMove != gs.getChosenMove()) {
            boardPresenter.setAvailableTiles(playerModel.getMove(gs.getChosenMove()).getAccessibleCells(playerModel.getPosition(), gs.getBoard()));
            lastBoardPosition = playerModel.getPosition();
            lastChosenMove = gs.getChosenMove();
        }
        barsPresenter.setMana(playerModel.getCurrentMana());
        barsPresenter.setHealth(playerModel.getCurrentHealth());
        chessPresenter.setMoves(playerModel.getMoves());
        chessPresenter.selectMove(gs.getChosenMove());
    }

    private Vector2 getMousePosition() {
        Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        float mouseX = mouseWorldCoords.x;
        float mouseY = (windowHeight - mouseWorldCoords.y);
        return new Vector2(mouseX, mouseY);
    }

    public void render() {
        batch.begin();
        boardPresenter.render(batch);

        player.render(batch);

        chessPresenter.render(batch);
        barsPresenter.render(batch);
        buttonsPresenter.render(batch);
        batch.end();
    }

    public void choseMove(int chosenMove) {
        gs.setMove(chosenMove);
    }


    public void movePlayer(BoardPosition boardPosition) {
        gs.movePlayer(boardPosition);
    }

    public void endGame(GameResult gameResult) {
    }

    public void startTurn() {
    }

    public void increaseMana() {
        gs.increaseMana(CONST.DEFAULT_INCREASE_AMOUNT);
    }
}
