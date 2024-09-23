package com.io.core.board;

import com.io.CONST;
import com.io.core.character.CharacterEnum;
import com.io.core.character.MeleeEnemy;
import com.io.core.character.Player;
import com.io.core.moves.BishopMove;
import com.io.core.moves.MoveDTO;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.SnapshotEntity;
import com.io.service.snapshot.GameSnapshot;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardTest {

    GameSnapshot gameSnapshot;

    void init() {
        var playerEntity = mock(CharacterEntity.class);
        var enemyEntity = mock(CharacterEntity.class);
        var cellEntity = mock(CellEntity.class);
        var snapShotEntity = mock(SnapshotEntity.class);
        when(playerEntity.getCharacterEnum()).thenReturn(CharacterEnum.Player);
        when(playerEntity.getPosition()).thenReturn(new BoardPosition(0, 0));
        when(enemyEntity.getPosition()).thenReturn(new BoardPosition(2, 2));
        when(enemyEntity.getCurrentHealth()).thenReturn(2);

        when(enemyEntity.getCharacterEnum()).thenReturn(CharacterEnum.MeleeEnemy);
        when(enemyEntity.getTeam()).thenReturn(CONST.ENEMY_TEAM);
        when(snapShotEntity.getBoardHeight()).thenReturn(5);
        when(snapShotEntity.getBoardWidth()).thenReturn(4);


        gameSnapshot = mock(GameSnapshot.class);
        when(gameSnapshot.characterEntityList()).thenReturn(List.of(playerEntity, enemyEntity));
        when(gameSnapshot.cellEntityList()).thenReturn(List.of(cellEntity));
        when(gameSnapshot.snapshotEntity()).thenReturn(snapShotEntity);
    }

    @Test
    void testTryMakeMove_Valid() {
        init();
        var board = new Board(gameSnapshot);
        var moveChange = board.tryMakeMove(new MoveDTO(new BishopMove(0, 1, board),
                new BoardPosition(2, 2), board.getCharacter(new BoardPosition(0, 0))));
        assertTrue(moveChange.success());
        assertFalse(moveChange.hasMoved());
        assertTrue(moveChange.hasAttacked());
    }

    @Test
    void testTryMakeMove_InvalidCell() {
        init();
        var board = new Board(gameSnapshot);

        var moveChange = board.tryMakeMove(new MoveDTO(new BishopMove(0, 1, board),
                new BoardPosition(10, 10), board.getCharacter(new BoardPosition(0, 0))));
        assertFalse(moveChange.success());
        assertFalse(moveChange.hasMoved());
        assertFalse(moveChange.hasAttacked());
    }

    @Test
    void testTryMakeMove_InvalidMove() {
        init();
        var board = new Board(gameSnapshot);

        var moveChange = board.tryMakeMove(new MoveDTO(new BishopMove(0, 1, board),
                new BoardPosition(2, 1), board.getCharacter(new BoardPosition(0, 0))));
        assertFalse(moveChange.success());
        assertFalse(moveChange.hasMoved());
        assertFalse(moveChange.hasAttacked());
    }


    @Test
    void testGetBoardWidthAndHeight() {
        init();
        var board = new Board(gameSnapshot);

        assertEquals(5, board.getBoardHeight());
        assertEquals(4, board.getBoardWidth());
    }

    @Test
    void testIsValidCell() {
        init();
        var board = new Board(gameSnapshot);

        assertTrue(board.isValidCell(new BoardPosition(1, 1)));
        assertFalse(board.isValidCell(new BoardPosition(10, 10)));
    }

    @Test
    void testGetCharacter() {
        init();
        var board = new Board(gameSnapshot);

        assertInstanceOf(Player.class, board.getCharacter(new BoardPosition(0, 0)));
        assertInstanceOf(MeleeEnemy.class, board.getCharacter(new BoardPosition(2, 2)));
    }

    @Test
    void testGetCharacters() {
        init();
        var board = new Board(gameSnapshot);
        var characters = board.getCharacters();


        assertEquals(2, characters.size());
        assertEquals(1, characters.stream().filter((c) -> c instanceof Player).toList().size());

    }

    @Test
    void testGetTeamPosition() {
        init();
        var board = new Board(gameSnapshot);

        assertEquals(new BoardPosition(0, 0), board.getTeamPosition(CONST.PLAYER_TEAM).get(0));
        assertEquals(new BoardPosition(2, 2), board.getTeamPosition(CONST.ENEMY_TEAM).get(0));
    }

    @Test
    void testGetTeamCount() {
        init();
        var board = new Board(gameSnapshot);

        assertEquals(1, board.getTeamCount().get(CONST.PLAYER_TEAM));
    }
}