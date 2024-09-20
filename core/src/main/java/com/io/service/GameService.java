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
import com.io.presenter.GamePresenter;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    private int roomWidth;
    private int roomHeight;

    private TurnService ts;
    private GamePresenter gp;
    private SnapshotService sns;

    private Long gameSnapshotId;

    private boolean gameInProgress = false;
    private Board board;
    private Player player;

    public void init(TurnService ts, GamePresenter gp, SnapshotService sns, GameSnapshot gameSnapshot) {
        this.ts = ts;
        this.gp = gp;
        this.sns = sns;

        if (gameSnapshot == null) {
            gameSnapshotId = null;
            loadGame();
        } else {
            gameSnapshotId = gameSnapshot.getId();
            System.out.println("Game loaded from snapshot with id=" + gameSnapshotId);
            loadGame(gameSnapshot);
        }
    }

    public void loadGame() {
        var moves = List.of(
            new KingMove(2, 1),
            new KnightMove(3, 3),
            new RookMove(5, 4),
            new BishopMove(3, 2),
            new QueenMove(7, 5)
        );

        player = new Player(new BoardPosition(1, 0), moves);
        var characters = new ArrayList<>(List.of(
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

        ts.init(this, characters, board);
    }

    public void loadGame(GameSnapshot gameSnapshot) {
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

        var characters = new ArrayList<Character>();
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

        ts.init(this, characters, board);
    }

    public void startGame() {
        gp.startGame();
        gameInProgress = true;
    }

    void endGame(GameResult gameResult) {
        gp.endGame(gameResult);
        ts.stop();

        gameInProgress = false;
    }

    public void abort() {
        if (gameInProgress)
            createGameSnapshot(gameSnapshotId);
        else if (gameSnapshotId != null)
            sns.deleteSnapshot(gameSnapshotId);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Character> getCharacters() {
        return board.getCharacters();
    }

    public int getRoomWidth() {
        return roomWidth;
    }

    public int getRoomHeight() {
        return roomHeight;
    }

    public Board getBoardSnapshot() {
        // TODO: return readonly object
        return board;
    }

    GameResult checkEndGameCondition() {
        var teamCount = board.getTeamCount();
        if (teamCount.size() == 1) {
            return teamCount.containsKey(0) ? GameResult.WIN : GameResult.LOSE;
        }
        return GameResult.NONE;
    }

    public Character nextTurn() {
        return ts.nextTurn();
    }

    public boolean tryMakeMove(MoveDTO move) {
        boolean success = ts.tryMakeMove(move);

        if (success) {
            GameResult gameResult = checkEndGameCondition();
            if (gameResult != GameResult.NONE) endGame(gameResult);
        }
        return success;
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
        var characterEntityList = getCharacters().stream()
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

    public boolean moveEnemy(Enemy enemy) {
        var move = enemy.makeNextMove(board);
        if (move == null) {
            return false;
        }
        return tryMakeMove(move);
    }

    public boolean movePlayer(BoardPosition boardPosition, int chosenMove) {
        var move = player.getMove(chosenMove);
        return tryMakeMove(new MoveDTO(move, boardPosition, player));
    }
}
