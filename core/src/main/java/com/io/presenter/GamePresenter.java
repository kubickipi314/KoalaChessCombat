package com.io.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.Coordinator;
import com.io.core.CharacterType;
import com.io.core.board.BoardPosition;
import com.io.service.MoveResult;
import com.io.core.moves.Move;
import com.io.presenter.character.CharacterPresenterInterface;
import com.io.presenter.character.EnemyPresenter;
import com.io.presenter.character.PlayerPresenter;
import com.io.service.GameServiceInterface;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.io.core.CharacterType.PLAYER;

public class GamePresenter {
    private GameServiceInterface gs;
    private Coordinator coordinator;
    private SpriteBatch batch;
    private BoardPresenter boardPresenter;
    private ChessPresenter chessPresenter;
    private BarsPresenter barsPresenter;
    private ButtonsPresenter buttonsPresenter;

    protected float windowHeight;
    private boolean gameEnded = false;
    private float gameEndTime = 0;

    private Map<Integer, CharacterPresenterInterface> charactersMap;
    private List<Integer> charactersIdList;


    public void init(GameServiceInterface gs, Coordinator coordinator) {
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
        charactersIdList = new ArrayList<>();

        for (var register : characterRegisters) {
            int id = register.characterId();
            if (register.type() == PLAYER) {
                charactersIdList.add(id);
                charactersMap.put(id, new PlayerPresenter(tm, sm, cm, register.position()));
            } else {
                BoardPosition position = register.position();
                int health = register.maxHealth();
                CharacterType type = register.type();
                charactersIdList.add(id);
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
        if (gs.isPlayersTurn() && !gameEnded) {
            Vector2 mousePosition = getMousePosition();
            boardPresenter.handleInput(mousePosition);
            chessPresenter.handleInput(mousePosition);
            buttonsPresenter.handleInput(mousePosition);
        } else {
            gs.makeNextMove();
            MoveResult result = gs.getLastMoveResult();
            var enemy = charactersMap.get(result.characterId());
            if (result.hasMoved()) enemy.startMove(result.resultPosition());
            else enemy.startAttack(result.resultPosition());
            if (result.hasAttacked()) {
                if (result.isAttackedDead()) {
                    removeDeadCharacter(result.attackedId());
                }
            }
        }
        buttonsPresenter.update();
        updateCharacters();
        updateAvailableTiles();
        updateBars();
        updateGameEnd();
    }

    private void updateCharacters() {
        for (var characterId : charactersIdList) {
            var characterPresenter = charactersMap.get(characterId);
            characterPresenter.update();
        }
    }

    protected void updateAvailableTiles() {
        var selectedMove = chessPresenter.getSelectedMove();
        boardPresenter.setAvailableTiles(gs.getAvailableTiles(selectedMove));
    }

    private void updateBars() {
        barsPresenter.setMana(gs.getPlayerMana());
        barsPresenter.setHealth(gs.getPlayerHealth());
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

        for (var characterId : charactersIdList) {
            var characterPresenter = charactersMap.get(characterId);
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
            var player = charactersMap.get(result.characterId());
            if (result.hasMoved()) player.startMove(boardPosition);
            else player.startAttack(boardPosition);
            if (result.hasAttacked()) {
                if (result.isAttackedDead()) {
                    removeDeadCharacter(result.attackedId());
                } else {
                    EnemyPresenter enemy = (EnemyPresenter) charactersMap.get(result.attackedId());
                    enemy.setHealth(result.attackedHealth());
                }
            }
        } else {
            System.err.println("Failed to play Player's move");
        }
    }

    private void removeDeadCharacter(int characterId) {
        charactersIdList.remove(characterId);
    }

    public void endTurn() {
        gs.endPlayerTour();
    }
}
