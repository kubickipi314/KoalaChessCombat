package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;
import com.io.core.character.Enemy;
import com.io.core.character.Player;
import com.io.core.moves.Move;
import com.io.Coordinator;
import com.io.presenter.character.CharacterPresenterInterface;
import com.io.presenter.character.EnemyPresenter;
import com.io.presenter.character.PlayerPresenter;
import com.io.service.GameService;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.io.core.CharacterType.LINUX;

public class GamePresenter {
    private GameService gs;
    private Coordinator coordinator;
    private SpriteBatch batch;
    private BoardPresenter boardPresenter;
    private ChessPresenter chessPresenter;
    private BarsPresenter barsPresenter;
    private ButtonsPresenter buttonsPresenter;
    private Map<Character, CharacterPresenterInterface> charactersMap;
    private Player playerModel;
    protected float windowHeight;

    private BoardPosition lastBoardPosition = new BoardPosition(-1, -1);
    private int lastChosenMove = 0;
    private int currentHealth;
    private Character activeCharacter = null;
    private boolean gameEnded = false;
    private float gameEndTime = 0;

    public void init(GameService gs, Coordinator coordinator) {
        this.gs = gs;
        this.coordinator = coordinator;

        batch = new SpriteBatch();
        windowHeight = Gdx.graphics.getHeight();

        playerModel = gs.getPlayer();
        List<Move> moves = playerModel.getMoves();
        currentHealth = playerModel.getCurrentHealth();

        CoordinatesManager cm = new CoordinatesManager(gs.getRoomHeight(), gs.getRoomWidth(), moves.size());
        TextureManager tm = new TextureManager();
        SoundManager sm = new SoundManager();

        var characterModels = gs.getCharacters();
        charactersMap = new HashMap<>();
        for (var characterModel : characterModels) {
            if (characterModel instanceof Player) {
                charactersMap.put(characterModel, new PlayerPresenter(tm, sm, cm, characterModel.getPosition()));
            } else if (characterModel instanceof Enemy) {
                charactersMap.put(characterModel, new EnemyPresenter(tm, sm, cm, characterModel.getPosition(), characterModel.getMaxHealth(), LINUX));
            }
        }

        this.barsPresenter = new BarsPresenter(tm, cm);
        this.boardPresenter = new BoardPresenter(tm, cm, this);
        this.chessPresenter = new ChessPresenter(tm, sm, cm, moves);
        this.buttonsPresenter = new ButtonsPresenter(tm, sm, cm, this);
    }

    public void update() {
        if (activeCharacter == null && !gameEnded) {
            activeCharacter = gs.nextTurn();
        }
        var activePresenter = charactersMap.get(activeCharacter);
        if (!activePresenter.isActive() && !gameEnded) {
            if (activeCharacter instanceof Enemy enemyModel) {
                var success = enemyModel.makeNextMove();
                var enemyPresenter = charactersMap.get(enemyModel);
                if (currentHealth != playerModel.getCurrentHealth()) {
                    enemyPresenter.startAttack(playerModel.getPosition());
                }
                enemyPresenter.startMove(enemyModel.getPosition());
                if (!success) endTurn();
            } else {
                Vector2 mousePosition = getMousePosition();
                boardPresenter.handleInput(mousePosition);
                chessPresenter.handleInput(mousePosition);
                buttonsPresenter.handleInput(mousePosition);
            }
        }
        buttonsPresenter.update();
        updateCharacters();
        updateChess();
        updateBars();
        updateGameEnd();
    }

    private void updateCharacters() {
        for (var character : gs.getCharacters()) {
            var characterPresenter = charactersMap.get(character);
            if (characterPresenter instanceof EnemyPresenter enemyPresenter) {
                enemyPresenter.setHealth(character.getCurrentHealth());
            }
            characterPresenter.update();
        }
    }

    private void updateChess() {
        var selectedMove = chessPresenter.getSelectedMove();
        if (!lastBoardPosition.equals(playerModel.getPosition()) || lastChosenMove != selectedMove) {
            boardPresenter.setAvailableTiles(playerModel.getMove(selectedMove).getAccessibleCells(playerModel, gs.getBoardSnapshot()));
            lastBoardPosition = playerModel.getPosition();
            lastChosenMove = selectedMove;
        }
        chessPresenter.selectMove(selectedMove);
    }

    private void updateBars() {
        barsPresenter.setMana(playerModel.getCurrentMana());
        currentHealth = playerModel.getCurrentHealth();
        barsPresenter.setHealth(currentHealth);
    }

    private void updateGameEnd() {
        if (gameEnded) {
            gameEndTime += Gdx.graphics.getDeltaTime();
            if (gameEndTime >= 5) coordinator.setMenuScreen();
        }
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
        if (gs.getPlayer().makeNextMove(boardPosition, chosenMove)) {
            var playerPresenter = charactersMap.get(playerModel);
            playerPresenter.startMove(playerModel.getPosition());
            if (playerModel.getPosition() != boardPosition) playerPresenter.startAttack(boardPosition);
        } else {
            System.err.println("Failed to play Player's move");
        }
    }

    public void startGame() {
    }

    public void endGame(GameResult gameResult) {
        buttonsPresenter.showResult(gameResult);
        System.out.println("GAME END\tresult: " + gameResult);
        gameEnded = true;
    }

    public void endTurn() {
        activeCharacter = null;
    }
}
