package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.character.Player;
import com.io.service.GameService;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.bars_buttons.TourButton;

public class GamePresenter {
    private final SpriteBatch batch;
    private final BoardPresenter boardPresenter;
    private final ChessPresenter chessPresenter;
    private final BarsPresenter barsPresenter;
    private final TourButton tourButton;
    private final PlayerPresenter player;
    //private final EnemyPresenter enemy;
    protected final float windowHeight;
    private final CoordinatesManager cm;
    private final TextureManager tm = new TextureManager();
    private final SoundManager sm = new SoundManager();

    private final GameService gameService;

    BoardPosition lastBoardPosition = new BoardPosition(-1, -1);
    int lastChosenMove = -1;


    public GamePresenter(GameService gameService) {
        BoardPosition startingPosition = gameService.getPlayer().getPosition();

        batch = new SpriteBatch();
        this.gameService = gameService;
        this.cm = new CoordinatesManager(gameService.getRoomHeight(), gameService.getRoomWidth());
        this.player = new PlayerPresenter(tm, sm, cm, startingPosition);

        this.barsPresenter = new BarsPresenter(tm, sm, cm);
        //this.enemy = new EnemyPresenter(tm, sm, cm);

        this.boardPresenter = new BoardPresenter(tm, cm, this);
        this.chessPresenter = new ChessPresenter(tm, sm, cm, this);
        this.tourButton = barsPresenter.getTourButton();

        windowHeight = Gdx.graphics.getHeight();
    }

    public void update() {
        boolean active = false;
        if (player.isActive()) {
            player.updatePosition();
            active = true;
        }
//        if (enemy.isActive()) {
//            enemy.updatePosition();
//            active = true;
//        }

        if (!active) {

            // temporary solution presenter might need more information

            Player playerModel = gameService.getPlayer();
            player.startMoveAnimation(playerModel.getPosition().x(), playerModel.getPosition().y());
            if (!lastBoardPosition.equals(playerModel.getPosition()) || lastChosenMove != gameService.getChosenMove()) {
                boardPresenter.setAvailableTiles(playerModel.getMove(gameService.getChosenMove()).getAccessibleCells(playerModel.getPosition(), gameService.getBoard()));
                lastBoardPosition = playerModel.getPosition();
                lastChosenMove = gameService.getChosenMove();
            }
            barsPresenter.setMana(playerModel.getCurrentMana());
            barsPresenter.setHealth(playerModel.getCurrentHealth());
            chessPresenter.setMoves(playerModel.getMoves());
            chessPresenter.selectMove(gameService.getChosenMove());


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
        //enemy.render(batch);

        chessPresenter.render(batch);

        barsPresenter.render(batch);
        tourButton.draw(batch);
        batch.end();
    }

    public void choseMove(int chosenMove) {
        gameService.setMove(chosenMove);
    }

    private void handleTourButton(Vector2 mousePosition) {
        if (tourButton.contains(mousePosition)) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                gameService.increaseMana(CONST.DEFAULT_INCREASE_AMOUNT);
                barsPresenter.playSwordSound();
                //enemy.move();
            }
        }
    }

    public boolean movePlayer(BoardPosition boardPosition) {
        return gameService.movePlayer(boardPosition);
    }

    public void endGame(GameResult gameResult) {
    }

    public void startTurn() {
    }
}
