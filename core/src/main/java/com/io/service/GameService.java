package com.io.service;

import com.io.CONST;
import com.io.core.GameResult;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.character.Character;
import com.io.core.character.MeleeEnemy;
import com.io.core.character.Player;
import com.io.core.moves.*;
import com.io.presenter.GamePresenter;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    private final int roomWidth = CONST.DEFAULT_ROOM_WIDTH;
    private final int roomHeight = CONST.DEFAULT_ROOM_HEIGHT;

    private GamePresenter gp;
    private TurnService ts;

    private Board board;
    private Player player;

    public void init(TurnService ts, GamePresenter gp) {
        this.ts = ts;
        this.gp = gp;

        BoardPosition playerStartingPosition = new BoardPosition(1, 0);
        var moves = List.of(new Move[]{
            new KingMove(2, 1),
            new KnightMove(3, 3),
            new RookMove(5, 4),
            new BishopMove(3, 2),
            new QueenMove(7, 5)
        });
        player = new Player(this, gp, playerStartingPosition, moves);

        var characters = new ArrayList<>(List.of(
            player,
            new MeleeEnemy(this, gp, new BoardPosition(1, 4)),
            new MeleeEnemy(this, gp, new BoardPosition(2, 4)),
            new MeleeEnemy(this, gp, new BoardPosition(3, 4))
        ));

        board = new Board(roomWidth, roomHeight, characters);

        ts.init(this, characters, board);
    }

    public void startGame() {
        gp.startGame();
    }

    void endGame(GameResult gameResult) {
        gp.endGame(gameResult);
        ts.stop();
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

}
