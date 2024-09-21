package com.io.service;

import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.board.SpecialCell;
import com.io.core.character.Character;
import com.io.core.character.*;
import com.io.core.moves.*;
import com.io.core.snapshot.GameSnapshot;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.SnapshotEntity;
import com.io.presenter.character.CharacterTypesMapper;

import java.util.*;

public class GameService implements GameServiceInterface {
    private int roomWidth;
    private int roomHeight;
    private SnapshotService sns;
    private Long gameSnapshotId;
    private boolean gameInProgress = false;
    private boolean gameEnded = false;
    private Board board;
    private Player player;
    private List<Character> characters;
    private Map<Character, Integer> characterIdMap;
    private List<MoveResult> movesHistory;
    private Queue<Character> turnQueue;

    public void init(SnapshotService sns, GameSnapshot gameSnapshot) {
        this.sns = sns;
        movesHistory = new ArrayList<>();

        if (gameSnapshot == null) {
            gameSnapshotId = null;
            loadGame();
        } else {
            gameSnapshotId = gameSnapshot.getId();
            System.out.println("Game loaded from snapshot with id=" + gameSnapshotId);
            loadGame(gameSnapshot);
        }

        movesHistory = new ArrayList<>();

        turnQueue = new LinkedList<>(characters);
    }

    private void loadGame() {
        var moves = List.of(
                new KingMove(2, 1),
                new KnightMove(3, 3),
                new RookMove(5, 4),
                new BishopMove(3, 2),
                new QueenMove(7, 5)
        );
        player = new Player(new BoardPosition(1, 0), moves);

        characters = new ArrayList<>(List.of(
                player,
                new MeleeEnemy(new BoardPosition(1, 4)),
                new MeleeEnemy(new BoardPosition(2, 4)),
                new MeleeEnemy(new BoardPosition(3, 3))
        ));

        roomWidth = CONST.DEFAULT_ROOM_WIDTH;
        roomHeight = CONST.DEFAULT_ROOM_HEIGHT;

        var specialCells = List.of(
                new SpecialCell(2, 2, true)
        );

        board = new Board(roomWidth, roomHeight, characters, specialCells);

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

    @Override
    public List<CharacterRegister> getCharacterRegisters() {
        characterIdMap = new HashMap<>();
        List<CharacterRegister> characterRegisters = new ArrayList<>();

        int idCounter = 0;
        for (var character : characters) {
            characterIdMap.put(character, idCounter);

            var type = CharacterTypesMapper.getPresenterType(getCharacterEnum(character));
            var position = character.getPosition();
            var health = character.getCurrentHealth();
            characterRegisters.add(new CharacterRegister(idCounter, type, position, health));
            idCounter++;
        }
        return characterRegisters;
    }

    @Override
    public List<Move> getPlayerMoves() {
        return player.getMoves();
    }

    @Override
    public boolean hasGameEnded() {
        return gameEnded;
    }

    @Override
    public GameResult checkEndGameCondition() {
        var teamCount = board.getTeamCount();
        if (teamCount.size() == 1) {
            return teamCount.containsKey(0) ? GameResult.WIN : GameResult.LOSE;
        }
        return GameResult.NONE;
    }

    @Override
    public boolean isPlayersTurn() {
        return turnQueue.peek() instanceof Player;
    }

    @Override
    public boolean movePlayer(BoardPosition boardPosition, Move move) {
        if (!isPlayersTurn()) return false;
        var moveDTO = new MoveDTO(move, boardPosition, player);
        if (board.tryMakeMove(new MoveDTO(move, boardPosition, player))) {
            collectMoveInformation(moveDTO);
            if (checkEndGameCondition() != GameResult.NONE) endGame();
            return true;
        }
        return false;
    }

    @Override
    public void endPlayerTour() {
        if (isPlayersTurn())
            turnQueue.add(turnQueue.poll());
    }

    @Override
    public boolean makeNextMove() {
        if (isPlayersTurn()) return false;

        assert !turnQueue.isEmpty();
        Enemy enemy = (Enemy) turnQueue.peek();
        enemy.changeMana(5);
        var moveDTO = enemy.makeNextMove(board);
        if (moveDTO == null) {
            nextTurn();
            return makeNextMove();
        }
        if (board.tryMakeMove(moveDTO)){
            collectMoveInformation(moveDTO);
            if (checkEndGameCondition() != GameResult.NONE) endGame();
            return true;
        }
        nextTurn();
        return makeNextMove();
    }

    private void collectMoveInformation(MoveDTO move) {
        Character character = move.character();
        int characterId = characterIdMap.get(character);
        boolean hasMoved = board.hasMoved();
        BoardPosition resultPosition = character.getPosition();
        boolean hasAttacked = board.hasAttacked();
        Character attacked = board.getAttacked();
        boolean isAttackedDead = board.isAttackedDead();
        int attackedId = characterIdMap.get(attacked);
        int attackedHealth = attacked.getCurrentHealth();

        MoveResult result = new MoveResult(characterId, hasMoved, resultPosition,
                hasAttacked, isAttackedDead, attackedId, attackedHealth);
        movesHistory.add(result);
    }

    private void nextTurn() {
        turnQueue.add(turnQueue.poll());
        Character enemy = turnQueue.peek();
        while (true) {
            assert enemy != null;
            if (!enemy.isDead()) break;
            turnQueue.poll();
            enemy = turnQueue.peek();
        }
    }

    @Override
    public MoveResult getLastMoveResult() {
        int historySize = movesHistory.size();
        return movesHistory.get(historySize - 1);
    }

    @Override
    public List<BoardPosition> getAvailableTiles(Move move) {
        return move.getAccessibleCells(player, getBoardSnapshot());
    }

    @Override
    public int getPlayerHealth() {
        return player.getCurrentHealth();
    }

    @Override
    public int getPlayerMana() {
        return player.getCurrentMana();
    }

    @Override
    public List<SpecialCell> getSpecialCells() {
        return board.getSpecialCells();
    }

    @Override
    public int getRoomWidth() {
        return roomWidth;
    }

    @Override
    public int getRoomHeight() {
        return roomHeight;
    }

    public void startGame() {
        gameInProgress = true;
    }

    private void endGame() {
        gameEnded = true;
        gameInProgress = false;
    }

    public void abort() {
        if (gameInProgress)
            createGameSnapshot(gameSnapshotId);
        else if (gameSnapshotId != null)
            sns.deleteSnapshot(gameSnapshotId);
    }

    private Board getBoardSnapshot() {
        // TODO: return readonly object
        return board;
    }

    private CharacterEnum getCharacterEnum(Character character) {
        if (character instanceof Player)
            return CharacterEnum.Player;
        if (character instanceof MeleeEnemy) {
            return CharacterEnum.MeleeEnemy;
        }
        return null;
    }

    private void createGameSnapshot(Long id) {
        var snapshotEntity = new SnapshotEntity(board.boardWidth, board.boardHeight);
        var characterEntityList = characters.stream()
                .map(ch -> new CharacterEntity(
                        ch.getPosition().x(),
                        ch.getPosition().y(),
                        getCharacterEnum(ch),
                        ch.getCurrentHealth(),
                        ch.getCurrentMana(),
                        ch.getTeam()
                )).toList();
        var cellEntityList = board.getSpecialCells().stream()
                .map(cell -> new CellEntity(
                        cell.x(),
                        cell.y(),
                        cell.isBlocked()
                )).toList();

        sns.createSnapshot(id, snapshotEntity, characterEntityList, cellEntityList);
    }
}
