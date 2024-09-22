package com.io.core.board;

import com.io.core.character.Character;
import com.io.core.moves.MoveDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Cell[][] board;
    public final int boardWidth, boardHeight;

    private final List<Character> characters;
    private final Map<Integer, Integer> teamCount;

    public Board(int width, int height, List<Character> characters, List<SpecialCell> specialCells) {
        this.board = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell(false);
            }
        }
        for (var cell : specialCells) {
            board[cell.y()][cell.x()].setBlocked(cell.isBlocked());
        }

        this.characters = characters;
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

    public boolean tryMakeMove(MoveDTO moveDTO) {
        var move = moveDTO.move();
        var movePosition = moveDTO.boardPosition();
        var character = moveDTO.character();
        var destinationCell = getCell(movePosition);
        var startCell = getCell(character.getPosition());
        var attackedCharacter = destinationCell.getCharacter();

        if (character.getCurrentMana() < move.getCost()) return false;
        if (attackedCharacter != null && character.getTeam() == attackedCharacter.getTeam()) return false;
        if (!move.isMoveValid(character, movePosition, this)) return false;

        character.changeMana(-move.getCost());
        boolean moveCharacter = false;
        if (attackedCharacter != null) {
            attackedCharacter.changeHealth(-move.getDamage());
            if (attackedCharacter.getCurrentHealth() <= 0) {
                characters.remove(attackedCharacter);
                decreaseTeamCount(attackedCharacter.getTeam());
                destinationCell.setCharacter(null);
            }
            moveCharacter = move.moveOnKill();
        } else {
            moveCharacter = true;
        }

        if (moveCharacter) {
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
}
