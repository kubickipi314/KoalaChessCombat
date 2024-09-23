package com.io.service;

import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.Board;
import com.io.core.board.BoardMoveChange;
import com.io.core.board.BoardPosition;
import com.io.core.board.SpecialCell;
import com.io.core.character.CharacterEnum;
import com.io.core.moves.Move;
import com.io.core.moves.MoveDTO;
import com.io.core.snapshot.GameSnapshot;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.presenter.game.GameServiceInterface;
import com.io.presenter.game.components.MoveData;
import com.io.service.utils.CharacterRegister;
import com.io.service.utils.CharacterTypesMapper;
import com.io.service.utils.MoveResult;

import java.util.*;

public class GameService implements GameServiceInterface {
    private final int roomWidth;
    private final int roomHeight;
    private final SnapshotService sns;
    private final long levelId;
    private final GameSnapshot gameSnapshot;
    private boolean gameEnded = false;
    private final BoardInterface board;
    private final PlayerInterface player;
    private final List<? extends CharacterInterface> characters;
    private Map<CharacterInterface, Integer> characterIdMap;
    private List<MoveResult> movesHistory;
    private final Queue<CharacterInterface> turnQueue;

    public GameService(SnapshotService sns, long levelId, BoardInterface board, List<? extends CharacterInterface> characters) {
        this.sns = sns;
        this.board = board;
        this.player = board.getPlayer();
        this.characters = characters;
        this.roomHeight = board.getBoardHeight();
        this.roomWidth = board.getBoardWidth();
        movesHistory = new ArrayList<>();

        this.levelId = levelId;
        this.gameSnapshot = sns.getLevelSnapshot(levelId);
        movesHistory = new ArrayList<>();

        turnQueue = new LinkedList<>(characters);
    }

    public List<CharacterRegister> getCharacterRegisters() {
        characterIdMap = new HashMap<>();
        List<CharacterRegister> characterRegisters = new ArrayList<>();

        int idCounter = 0;
        for (var character : characters) {
            characterIdMap.put(character, idCounter);

            var type = CharacterTypesMapper.getPresenterType(getCharacterEnum(character));
            var position = character.getPosition();
            var health = character.getCurrentHealth();
            characterRegisters.add(new CharacterRegister(character.getType() == CharacterEnum.Player, idCounter, type, position, health));
            idCounter++;
        }
        return characterRegisters;
    }

    public List<MoveData> getPlayerMovesData() {
        List<MoveData> movesData = new ArrayList<>();
        for (var move : player.getMoves()){
            movesData.add(new MoveData(move.getType(), move.getCost(), move.getDamage()));
        }
        return movesData;
    }

    public boolean hasGameEnded() {
        return gameEnded;
    }

    public GameResult checkEndGameCondition() {
        var teamCount = board.getTeamCount();
        if (teamCount.size() == 1) {
            return teamCount.containsKey(CONST.PLAYER_TEAM) ? GameResult.WIN : GameResult.LOSE;
        }
        return GameResult.NONE;
    }

    public boolean isPlayersTurn() {
        if (turnQueue.isEmpty()) return false;
        return turnQueue.peek().getType() == CharacterEnum.Player;
    }

    public boolean movePlayer(BoardPosition boardPosition, int moveNumber) {
        if (!isPlayersTurn()) return false;
        Move move = player.getMove(moveNumber);
        var moveDTO = new MoveDTO(move, boardPosition, player);
        var boardMoveChange = board.tryMakeMove(moveDTO);
        if (boardMoveChange.success()) {
            addMoveHistory(boardMoveChange);
            if (checkEndGameCondition() != GameResult.NONE) endGame();
            return true;
        }
        return false;
    }

    public void endPlayerTurn() {
        if (isPlayersTurn()) {
            turnQueue.add(turnQueue.poll());
            player.changeMana(5);
        }
    }

    public boolean makeNextMove() {
        if (isPlayersTurn()) return false;

        assert !turnQueue.isEmpty();
        EnemyInterface enemy = (EnemyInterface) turnQueue.peek();

        if (enemy.isDead()) {
            turnQueue.poll();
            return false;
        }
        var moveDTO = enemy.makeNextMove();
        if (moveDTO == null) {
            enemy.changeMana(5);
            turnQueue.add(turnQueue.poll());
            return false;
        }
        var boardMoveChange = board.tryMakeMove(moveDTO);
        if (boardMoveChange.success()) {
            addMoveHistory(boardMoveChange);
            if (checkEndGameCondition() != GameResult.NONE) endGame();
            return true;
        }
        turnQueue.add(turnQueue.poll());
        return false;
    }

    private void addMoveHistory(BoardMoveChange boardMoveChange) {
        MoveResult result;
        CharacterInterface character = boardMoveChange.move().character();
        int characterId = characterIdMap.get(character);
        boolean hasMoved = boardMoveChange.hasMoved();
        BoardPosition targetPosition = boardMoveChange.move().boardPosition();
        boolean hasAttacked = boardMoveChange.hasAttacked();
        if (hasAttacked) {
            var attacked = boardMoveChange.attacked();
            boolean isAttackedDead = attacked.isDead();
            int attackedId = characterIdMap.get(attacked);
            int attackedHealth = attacked.getCurrentHealth();
            result = new MoveResult(characterId, hasMoved, targetPosition,
                true, isAttackedDead, attackedId, attackedHealth);
        } else {
            result = new MoveResult(characterId, hasMoved, targetPosition,
                false, false, -1, 0);
        }
        movesHistory.add(result);
    }

    public MoveResult getLastMoveResult() {
        int historySize = movesHistory.size();
        return movesHistory.get(historySize - 1);
    }

    public List<BoardPosition> getAvailableTiles(int moveNumber) {
        Move move = player.getMove(moveNumber);
        return move.getAccessibleCells(player.getPosition());
    }

    public int getPlayerHealth() {
        return player.getCurrentHealth();
    }

    public int getPlayerMana() {
        return player.getCurrentMana();
    }

    public List<SpecialCell> getSpecialCells() {
        return board.getSpecialCells();
    }

    public int getRoomWidth() {
        return roomWidth;
    }

    public int getRoomHeight() {
        return roomHeight;
    }

    private void endGame() {
        gameEnded = true;
        sns.removeLevelSnapshot(levelId);
    }

    public void abort() {
        if (!gameEnded)
            createSnapshot();
    }

    private CharacterEnum getCharacterEnum(CharacterInterface character) {
        return character.getType();
    }

    private void createSnapshot() {
        var snapshotEntity = gameSnapshot.snapshotEntity();
        var characterEntityList = characters.stream()
            .map(ch -> new CharacterEntity(
                ch.getPosition().x(),
                ch.getPosition().y(),
                getCharacterEnum(ch),
                ch.getTeam(),
                ch.getCurrentHealth(),
                ch.getCurrentMana()
            )).toList();
        var cellEntityList = board.getSpecialCells().stream()
            .map(cell -> new CellEntity(
                cell.x(),
                cell.y(),
                cell.isBlocked()
            )).toList();

        sns.createLevelSnapshot(levelId, snapshotEntity, characterEntityList, cellEntityList);
    }
}
