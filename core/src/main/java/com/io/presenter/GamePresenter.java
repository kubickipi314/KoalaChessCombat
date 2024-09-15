package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;
import com.io.core.character.Player;
import com.io.service.GameService;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;

import java.util.HashMap;
import java.util.Map;

public class GamePresenter {
    private GameService gs;

    private SpriteBatch batch;
    private BoardPresenter boardPresenter;
    private ChessPresenter chessPresenter;
    private BarsPresenter barsPresenter;
    private ButtonsPresenter buttonsPresenter;
    private Map<Character, CharacterPresenter> charactersMap;
    private Player playerModel;
    protected float windowHeight;

    private BoardPosition lastBoardPosition = new BoardPosition(-1, -1);
    private int lastChosenMove = -1;

    public void init(GameService gs) {
        this.gs = gs;

        batch = new SpriteBatch();
        CoordinatesManager cm = new CoordinatesManager(gs.getRoomHeight(), gs.getRoomWidth(), 5); // TODO
        TextureManager tm = new TextureManager();
        SoundManager sm = new SoundManager();

        var characterModels = gs.getCharacters();
        charactersMap = new HashMap<>();
        for (var characterModel : characterModels) {
            charactersMap.put(characterModel, new CharacterPresenter(tm, sm, cm, characterModel.getPosition()));
        }
        playerModel = gs.getPlayer();

        this.barsPresenter = new BarsPresenter(tm, cm);

        this.boardPresenter = new BoardPresenter(tm, cm, this);
        this.chessPresenter = new ChessPresenter(tm, sm, cm);
        this.buttonsPresenter = new ButtonsPresenter(tm, sm, cm, this);

        windowHeight = Gdx.graphics.getHeight();
    }

    public void update() {
        for (var characterModel : gs.getCharacters()) {
            var characterPresenter = charactersMap.get(characterModel);
            if (characterPresenter.isMoving())
                characterPresenter.updatePosition();
        }

        if (!charactersMap.get(playerModel).isMoving()) {
            updateFromModel();

            Vector2 mousePosition = getMousePosition();
            boardPresenter.handleInput(mousePosition);
            chessPresenter.handleInput(mousePosition);
            buttonsPresenter.handleInput(mousePosition);
        }
    }

    private void updateFromModel() {
        // temporary solution presenter might need more information

        for (var characterModel : gs.getCharacters()) {
            var characterPresenter = charactersMap.get(characterModel);
            characterPresenter.update(characterModel.getPosition());
        }

        var selectedMove = chessPresenter.getSelectedMove();
        if (!lastBoardPosition.equals(playerModel.getPosition()) || lastChosenMove != selectedMove) {
            boardPresenter.setAvailableTiles(playerModel.getMove(selectedMove).getAccessibleCells(playerModel.getPosition(), gs.getBoardSnapshot()));
            lastBoardPosition = playerModel.getPosition();
            lastChosenMove = selectedMove;
        }
        barsPresenter.setMana(playerModel.getCurrentMana());
        barsPresenter.setHealth(playerModel.getCurrentHealth());
        chessPresenter.setMoves(playerModel.getMoves());
        chessPresenter.selectMove(selectedMove);
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

        for (var characterModel : gs.getCharacters()) {
            var characterPresenter = charactersMap.get(characterModel);
            characterPresenter.render(batch);
        }

        chessPresenter.render(batch);
        barsPresenter.render(batch);
        buttonsPresenter.render(batch);

        batch.end();
    }

    public void movePlayer(BoardPosition boardPosition) {
        var chosenMove = chessPresenter.getSelectedMove();
        if (!gs.getPlayer().PlayMove(boardPosition, chosenMove)) {
            System.err.println("Failed to play Player's move");
        }
    }

    public void endGame(GameResult gameResult) {
    }

    public void startTurn() {
    }

    public void endTurn() {
        gs.endTurn();
    }
}
