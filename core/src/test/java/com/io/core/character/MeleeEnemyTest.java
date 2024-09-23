package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.Move;
import com.io.core.moves.MoveDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeleeEnemyTest {
    Player player;
    Board smallBoard;

    void init() {
        player = mock(Player.class);
        when(player.getPosition()).thenReturn(new BoardPosition(0, 0));
        when(player.getTeam()).thenReturn(CONST.PLAYER_TEAM);

        smallBoard = mock(Board.class);
        when(smallBoard.isValidCell(any(BoardPosition.class))).thenReturn(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(smallBoard.isValidCell(new BoardPosition(i, j))).thenReturn(true);
            }
        }
        when(smallBoard.getBoardHeight()).thenReturn(3);
        when(smallBoard.getBoardWidth()).thenReturn(3);
        when(smallBoard.getCharacter(new BoardPosition(0, 0))).thenReturn(player);
        when(smallBoard.getTeamPosition(CONST.PLAYER_TEAM)).thenReturn(List.of(new BoardPosition(0, 0)));
    }

    @Test
    void testMakeKingMove() {
        init();
        var meleeEnemy = new MeleeEnemy(new BoardPosition(2, 2), smallBoard);
        meleeEnemy.changeMana(-1);
        assertEquals(meleeEnemy.getTeam(), CONST.ENEMY_TEAM);
        when(smallBoard.getCharacter(new BoardPosition(2, 2))).thenReturn(meleeEnemy);

        var availablePositions = List.of(new BoardPosition(1, 2), new BoardPosition(1, 1));
        var move = meleeEnemy.makeNextMove();
        assertNotNull(move);
        assertTrue(availablePositions.contains(move.boardPosition())); // attacked player
        assertEquals(move.move().getClass(), KingMove.class);
        meleeEnemy.changeMana(-2);

        move = meleeEnemy.makeNextMove();
        assertNull(move);
    }

    @Test
    void testAttackMove() {
        init();
        var meleeEnemy = new MeleeEnemy(new BoardPosition(1, 1), smallBoard);
        meleeEnemy.changeMana(-1);
        assertEquals(meleeEnemy.getTeam(), CONST.ENEMY_TEAM);
        when(smallBoard.getCharacter(new BoardPosition(1, 1))).thenReturn(meleeEnemy);

        var move = meleeEnemy.makeNextMove();
        assertNotNull(move);
        assertEquals(move.boardPosition(), new BoardPosition(0,0)); // attacked player
        assertEquals(move.move().getClass(), KingMove.class);
        meleeEnemy.changeMana(-2);

        move = meleeEnemy.makeNextMove();
        assertNull(move);
    }
}
