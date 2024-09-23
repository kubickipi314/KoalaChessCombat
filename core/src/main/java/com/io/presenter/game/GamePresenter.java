package com.io.presenter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.Coordinator;
import com.io.core.board.BoardPosition;
import com.io.core.moves.Move;
import com.io.presenter.game.character.CharacterPresenterInterface;
import com.io.presenter.game.character.EnemyPresenter;
import com.io.presenter.game.character.PlayerPresenter;
import com.io.presenter.game.components.BarsPresenter;
import com.io.presenter.game.components.BoardPresenter;
import com.io.presenter.game.components.ButtonsPresenter;
import com.io.presenter.game.components.ChessPresenter;
import com.io.service.utils.MoveResult;
import com.io.view.game.SoundManager;
import com.io.view.game.TextureManager;
import com.io.view.game.characters.CharacterViewType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePresenter {
    private final GameServiceInterface gs;
    private final Coordinator coordinator;
    private final SpriteBatch batch;
    private final BoardPresenter boardPresenter;
    private final ChessPresenter chessPresenter;
    private final BarsPresenter barsPresenter;
    private final ButtonsPresenter buttonsPresenter;

    protected float windowHeight;
    private boolean gameEnded = false;
    private float gameEndTime = 0;

    private final Map<Integer, CharacterPresenterInterface> charactersMap;


    public GamePresenter(GameServiceInterface gs, Coordinator coordinator) {
        this.gs = gs;
        this.coordinator = coordinator;

        batch = new SpriteBatch();
        windowHeight = Gdx.graphics.getHeight();


        List<Move> moves = gs.getPlayerMoves();

        CoordinatesManager cm = new CoordinatesManager(gs.getRoomHeight(), gs.getRoomWidth(), moves.size());
        TextureManager tm = new TextureManager();
        SoundManager sm = new SoundManager();

        var characterRegisters = gs.getCharacterRegisters();
        charactersMap = new HashMap<>();

        for (var register : characterRegisters) {
            int id = register.characterId();
            CharacterViewType type = register.type();
            if (register.player()) {
                charactersMap.put(id, new PlayerPresenter(tm, sm, cm, register.position(), type));
            } else {
                BoardPosition position = register.position();
                int health = register.maxHealth();
                charactersMap.put(id, new EnemyPresenter(tm, sm, cm, position, health, type));
            }
        }

        var specialCells = gs.getSpecialCells();

        this.barsPresenter = new BarsPresenter(tm, cm);
        this.boardPresenter = new BoardPresenter(tm, cm, this, specialCells);
        this.chessPresenter = new ChessPresenter(tm, sm, cm, this, moves);
        this.buttonsPresenter = new ButtonsPresenter(tm, sm, cm, this);
    }

    public void update() {
        if (gs.hasGameEnded()) {
            gameEnded = true;
            buttonsPresenter.showResult(gs.checkEndGameCondition());
        }
        if (!isActiveCharacter() && !gameEnded) {
            if (gs.isPlayersTurn()) {
                Vector2 mousePosition = getMousePosition();
                boardPresenter.handleInput(mousePosition);
                chessPresenter.handleInput(mousePosition);
                buttonsPresenter.handleInput(mousePosition);
            } else {
                boolean success = gs.makeNextMove();
                if (!success) return;
                MoveResult result = gs.getLastMoveResult();
                handleEnemyMove(result);
            }
        }
        buttonsPresenter.update();
        updateCharacters();
        updateAvailableTiles();
        updateBars();
        updateGameEnd();
    }

    private boolean isActiveCharacter() {
        for (var characterPresenter : charactersMap.values()) {
            if (characterPresenter.isActive()) return true;
        }
        return false;
    }

    private void updateCharacters() {
        for (var characterPresenter : charactersMap.values()) {
            characterPresenter.update();
        }
    }

    public void updateAvailableTiles() {
        var selectedMove = chessPresenter.getSelectedMove();
        boardPresenter.setAvailableTiles(gs.getAvailableTiles(selectedMove));
    }

    private void updateBars() {
        barsPresenter.setMana(gs.getPlayerMana());
        barsPresenter.setHealth(gs.getPlayerHealth());
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

        for (var characterPresenter : charactersMap.values()) {
            characterPresenter.render(batch);
        }

        chessPresenter.render(batch);
        barsPresenter.render(batch);
        buttonsPresenter.render(batch);

        batch.end();
    }

    public void movePlayer(BoardPosition boardPosition) {
        var chosenMove = chessPresenter.getSelectedMove();
        if (gs.movePlayer(boardPosition, chosenMove)) {
            MoveResult result = gs.getLastMoveResult();
            handlePlayerMove(result);
        } else {
            System.err.println("Failed to play Player's move");
        }
    }

    public void handlePlayerMove(MoveResult result) {
        var player = charactersMap.get(result.characterId());
        if (result.hasMoved()) player.startMove(result.targetPosition());
        else player.startAttack(result.targetPosition());
        if (result.hasAttacked()) {
            if (result.isAttackedDead()) {
                removeDeadCharacter(result.attackedId());
            } else {
                EnemyPresenter enemy = (EnemyPresenter) charactersMap.get(result.attackedId());
                enemy.setHealth(result.attackedHealth());
            }
        }
    }

    public void handleEnemyMove(MoveResult result) {
        var enemy = charactersMap.get(result.characterId());
        if (result.hasMoved()) enemy.startMove(result.targetPosition());
        else enemy.startAttack(result.targetPosition());
        if (result.hasAttacked()) {
            if (result.isAttackedDead()) {
                removeDeadCharacter(result.attackedId());
            }
        }
    }

    private void removeDeadCharacter(Integer characterId) {
        charactersMap.remove(characterId);
    }

    public void endTurn() {
        gs.endPlayerTurn();
    }

    private void updateGameEnd() {
        if (gameEnded) {
            gameEndTime += Gdx.graphics.getDeltaTime();
            if (gameEndTime >= 5) coordinator.setMenuScreen();
        }
    }

    public void exitGame() {
        coordinator.exitGame();
    }
}
