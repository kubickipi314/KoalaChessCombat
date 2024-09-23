package com.io.core.board;

import com.io.core.character.Character;
import com.io.core.character.CharacterEnum;
import com.io.core.character.MeleeEnemy;
import com.io.core.character.Player;
import com.io.core.moves.*;
import com.io.core.snapshot.GameSnapshot;
import com.io.service.game.BoardInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardInterface {
    private final Cell[][] board;
    public final int boardWidth, boardHeight;

    private final List<Character> characters;
    private final Player player;
    private final Map<Integer, Integer> teamCount;

    private boolean hasMoved;
    private boolean hasAttacked;
    private Character attacked;

    public Board(GameSnapshot gameSnapshot) {
        var snapshotEntity = gameSnapshot.snapshotEntity();
        var characterEntityList = gameSnapshot.characterEntityList();
        var cellEntityList = gameSnapshot.cellEntityList();

        var moves = List.of(
                new KingMove(2, 1, this),
                new KnightMove(3, 3, this),
                new RookMove(5, 4, this),
                new BishopMove(3, 2, this),
                new QueenMove(7, 5, this)
        );

        characters = new ArrayList<>();
        Player playerCharacter = null;
        for (var che : characterEntityList) {
            var characterEnum = che.getCharacterEnum();
            Character character;
            if (characterEnum == CharacterEnum.Player) {
                playerCharacter = new Player(che, moves, this);
                character = playerCharacter;
            } else if (characterEnum == CharacterEnum.MeleeEnemy) {
                character = new MeleeEnemy(che, this);
            } else {
                System.err.println("Found unrecognised character(" + characterEnum + "when importing snapshot.");
                continue;
            }
            characters.add(character);
        }
        if (playerCharacter == null) {
            throw new Error("cannot make board without player");
        }
        this.player = playerCharacter;
        var width = snapshotEntity.getBoardWidth();
        var height = snapshotEntity.getBoardHeight();

        var specialCells = cellEntityList.stream()
                .map(ce -> new SpecialCell(
                        ce.getPositionX(),
                        ce.getPositionY(),
                        ce.isBlocked()
                )).toList();
        this.board = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell(false);
            }
        }
        for (var cell : specialCells) {
            board[cell.y()][cell.x()].setBlocked(cell.isBlocked());
        }

        for (var character : characters) {
            Cell cell = getCell(character.getPosition());
            cell.setCharacter(character);
        }
        boardWidth = width;
        boardHeight = height;

        teamCount = new HashMap<>();
        for (var character : characters) {
            var team = character.getTeam();
            if (teamCount.containsKey(team)) {
                var count = teamCount.get(team) + 1;
                teamCount.remove(team);
                teamCount.put(team, count);
            } else {
                teamCount.put(team, 1);
            }
        }

    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public boolean tryMakeMove(MoveDTO moveDTO) {
        hasMoved = false;
        hasAttacked = false;
        attacked = null;

        var move = moveDTO.move();
        var movePosition = moveDTO.boardPosition();
        var characterI = moveDTO.character();
        var character = getCharacter(characterI.getPosition());
        var destinationCell = getCell(movePosition);
        var startCell = getCell(characterI.getPosition());
        var attackedCharacter = destinationCell.getCharacter();

        if (character.getCurrentMana() < move.getCost()) return false;
        if (attackedCharacter != null && character.getTeam() == attackedCharacter.getTeam()) return false;
        if (!move.isMoveValid(character, movePosition)) return false;


        character.changeMana(-move.getCost());
        if (attackedCharacter != null) {
            attackedCharacter.changeHealth(-move.getDamage());
            hasAttacked = true;
            attacked = attackedCharacter;
            if (attackedCharacter.isDead()) {
                characters.remove(attackedCharacter);
                destinationCell.setCharacter(null);
                decreaseTeamCount(attackedCharacter.getTeam());
            }
        }
        if (destinationCell.getCharacter() == null) {
            hasMoved = true;
            destinationCell.setCharacter(character);
            character.setPosition(movePosition);
            startCell.setCharacter(null);
        }
        return true;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public boolean isValidCell(BoardPosition position) {
        if (position.x() < 0 || position.x() >= this.boardWidth) return false;
        if (position.y() < 0 || position.y() >= this.boardHeight) return false;
        return !getCell(position).isBlocked();
    }

    public Character getCharacter(BoardPosition position) {
        if (isValidCell(position))
            return board[position.y()][position.x()].getCharacter();
        return null;
    }

    private Cell getCell(BoardPosition position) {
        return board[position.y()][position.x()];
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<SpecialCell> getSpecialCells() {
        var specialCellList = new ArrayList<SpecialCell>();
        for (int y = 0; y < boardHeight; ++y) {
            for (int x = 0; x < boardWidth; ++x) {
                var cell = board[y][x];
                if (cell.isBlocked()) {
                    specialCellList.add(new SpecialCell(x, y, true));
                }
            }
        }
        return specialCellList;
    }

    public List<BoardPosition> getTeamPosition(int team) {
        var result = new ArrayList<BoardPosition>();
        for (int y = 0; y < boardHeight; ++y) {
            for (int x = 0; x < boardWidth; ++x) {
                Character character = getCell(new BoardPosition(x, y)).getCharacter();
                if (character != null && character.getTeam() == team)
                    result.add(new BoardPosition(x, y));
            }
        }
        return result;
    }

    void decreaseTeamCount(int team) {
        var count = teamCount.get(team) - 1;
        teamCount.remove(team);
        teamCount.put(team, count);

        if (count <= 0) teamCount.remove(team);
    }

    public Map<Integer, Integer> getTeamCount() {
        return teamCount;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public Character getAttacked() {
        return attacked;
    }
}
