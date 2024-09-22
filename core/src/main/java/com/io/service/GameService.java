package com.io.service;

import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.Board;
import com.io.core.board.BoardMoveChange;
import com.io.core.board.BoardPosition;
import com.io.core.board.SpecialCell;
import com.io.core.character.Character;
import com.io.core.character.*;
import com.io.core.moves.*;
import com.io.core.snapshot.GameSnapshot;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.presenter.game.GameServiceInterface;
import com.io.service.utils.CharacterRegister;
import com.io.service.utils.CharacterTypesMapper;
import com.io.service.utils.MoveResult;

import java.util.*;

public class GameService implements GameServiceInterface {
    private int roomWidth;
    private int roomHeight;
    private SnapshotService sns;
    private long levelId;
    private GameSnapshot gameSnapshot;
    private boolean gameEnded = false;
    private Board board;
    private Player player;
    private List<Character> characters;
    private Map<Character, Integer> characterIdMap;
    private List<MoveResult> movesHistory;
    private Queue<Character> turnQueue;

    public void init(SnapshotService sns, long levelId) {
        this.sns = sns;
        movesHistory = new ArrayList<>();

        this.levelId = levelId;
        this.gameSnapshot = sns.getLevelSnapshot(levelId);
        loadGame(gameSnapshot);

        movesHistory = new ArrayList<>();

        turnQueue = new LinkedList<>(characters);
    }

    private void loadGame(GameSnapshot gameSnapshot) {
        var snapshotEntity = gameSnapshot.snapshotEntity();
        var characterEntityList = gameSnapshot.characterEntityList();
        var cellEntityList = gameSnapshot.cellEntityList();

        var moves = List.of(
            new KingMove(2, 1),
            new KnightMove(3, 3),
            new RookMove(5, 4),
            new BishopMove(3, 2),
            new QueenMove(7, 5)
        );

        characters = new ArrayList<>();
        for (var che : characterEntityList) {
            var characterEnum = che.getCharacterEnum();
            Character character;
            if (characterEnum == CharacterEnum.Player) {
                player = new Player(che, moves);
                character = player;
            } else if (characterEnum == CharacterEnum.MeleeEnemy) {
                character = new MeleeEnemy(che);
            } else if (characterEnum == CharacterEnum.RangeEnemy) {
                character = new RangeEnemy(che);
            } else {
                System.err.println("Found unrecognised character(" + characterEnum + "when importing snapshot.");
                continue;
            }
            characters.add(character);
        }

        roomWidth = snapshotEntity.getBoardWidth();
        roomHeight = snapshotEntity.getBoardHeight();
        var specialCells = cellEntityList.stream()
            .map(ce -> new SpecialCell(
                ce.getPositionX(),
                ce.getPositionY(),
                ce.isBlocked()
            )).toList();
        board = new Board(roomWidth, roomHeight, characters, specialCells);
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
            characterRegisters.add(new CharacterRegister(character instanceof Player, idCounter, type, position, health));
            idCounter++;
        }
        return characterRegisters;
    }

    public List<Move> getPlayerMoves() {
        return player.getMoves();
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
        return turnQueue.peek() instanceof Player;
    }

    public boolean movePlayer(BoardPosition boardPosition, Move move) {
        if (!isPlayersTurn()) return false;
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
        Enemy enemy = (Enemy) turnQueue.peek();

        if (enemy.isDead()) {
            turnQueue.poll();
            return false;
        }
        var moveDTO = enemy.makeNextMove(board);
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
        Character character = boardMoveChange.move().character();
        int characterId = characterIdMap.get(character);
        boolean hasMoved = boardMoveChange.hasMoved();
        BoardPosition targetPosition = boardMoveChange.move().boardPosition();
        boolean hasAttacked = boardMoveChange.hasAttacked();
        if (hasAttacked) {
            Character attacked = boardMoveChange.attacked();
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

    public List<BoardPosition> getAvailableTiles(Move move) {
        return move.getAccessibleCells(player, board);
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

    private CharacterEnum getCharacterEnum(Character character) {
        if (character instanceof Player)
            return CharacterEnum.Player;
        if (character instanceof MeleeEnemy) {
            return CharacterEnum.MeleeEnemy;
        }
        if (character instanceof RangeEnemy) {
            return CharacterEnum.RangeEnemy;
        }
        return null;
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
