package com.io.service;

import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;
import com.io.core.character.CharacterEnum;
import com.io.core.character.MeleeEnemy;
import com.io.core.character.Player;
import com.io.core.moves.*;
import com.io.core.snapshot.CharacterSnapshot;
import com.io.core.snapshot.GameSnapshot;
import com.io.presenter.GamePresenter;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    private final int roomWidth = CONST.DEFAULT_ROOM_WIDTH;
    private final int roomHeight = CONST.DEFAULT_ROOM_HEIGHT;

    private TurnService ts;
    private GamePresenter gp;
    private SnapshotService sns;

    private GameSnapshot gameSnapshot;

    private boolean gameInProgress = false;
    private Board board;
    private Player player;


    public void init(TurnService ts, GamePresenter gp, SnapshotService sns, GameSnapshot gameSnapshot) {
        this.ts = ts;
        this.gp = gp;
        this.sns = sns;

        this.gameSnapshot = gameSnapshot;
        if (gameSnapshot != null)
            System.out.println("Snapshot ID: " + gameSnapshot.id());
        loadGame(gameSnapshot);
    }

    public void loadGame() {
        var moves = List.of(
            new KingMove(2, 1),
            new KnightMove(3, 3),
            new RookMove(5, 4),
            new BishopMove(3, 2),
            new QueenMove(7, 5)
        );

        player = new Player(this, gp, new BoardPosition(1, 0), moves);
        var characters = new ArrayList<>(List.of(
            player,
            new MeleeEnemy(this, gp, new BoardPosition(1, 4)),
            new MeleeEnemy(this, gp, new BoardPosition(2, 4)),
            new MeleeEnemy(this, gp, new BoardPosition(3, 4))
        ));

        board = new Board(roomWidth, roomHeight, characters);

        ts.init(this, characters, board);
    }

    public void loadGame(GameSnapshot gameSnapshot) {
        if (gameSnapshot == null) {
            loadGame();
            return;
        }

        var moves = List.of(new Move[]{
            new KingMove(2, 1),
            new KnightMove(3, 3),
            new RookMove(5, 4),
            new BishopMove(3, 2),
            new QueenMove(7, 5)
        });

        var characters = new ArrayList<Character>();

        for (var chs : gameSnapshot.characterSnapshotList()) {
            System.out.println(chs.characterEnum());
            var characterEnum = chs.characterEnum();
            var startPosition = new BoardPosition(chs.x(), chs.y());
            Character character;
            if (characterEnum == CharacterEnum.Player) {
                player = new Player(this, gp, startPosition, moves, chs.currentHealth(), chs.currentMana());
                character = player;
            } else if (characterEnum == CharacterEnum.MeleeEnemy) {
                character = new MeleeEnemy(this, gp, startPosition, chs.currentHealth(), chs.currentMana());
            } else {
                System.err.println("Found unrecognised character(" + characterEnum + "when importing snapshot.");
                continue;
            }
            characters.add(character);
        }

        board = new Board(roomWidth, roomHeight, characters);

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
            createGameSnapshot();
        else if (gameSnapshot != null)
            sns.deleteSnapshot(gameSnapshot.id());
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

    private void createGameSnapshot() {
        var characterSnapshots = new ArrayList<CharacterSnapshot>();
        for (var character : getCharacters()) {
            var x = character.getPosition().x();
            var y = character.getPosition().y();
            var characterEnum = getCharacterEnum(character);
            var currentHealth = character.getCurrentHealth();
            var currentMana = character.getCurrentMana();
            var team = character.getTeam();

            characterSnapshots.add(new CharacterSnapshot(x, y, characterEnum, currentHealth, currentMana, team));
        }
        var gameSnapshot = new GameSnapshot(null, characterSnapshots);
        sns.createSnapshot(gameSnapshot);
    }
}
