package com.io.core.character;

import com.io.CONST;
import com.io.core.board.Board;
import com.io.core.board.BoardPosition;
import com.io.core.moves.KingMove;
import com.io.core.moves.LongRangeMove;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RangeEnemyTest {
    Player player;
    Board smallBoard;

    void init() {
        player = mock(Player.class);
        when(player.getPosition()).thenReturn(new BoardPosition(0, 0));
        when(player.getTeam()).thenReturn(CONST.PLAYER_TEAM);

        smallBoard = mock(Board.class);
        when(smallBoard.isValidCell(any(BoardPosition.class))).thenReturn(false);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
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
        var meleeEnemy = new RangeEnemy(new BoardPosition(0, 1), smallBoard);
        assertEquals(meleeEnemy.getTeam(), CONST.ENEMY_TEAM);
        when(smallBoard.getCharacter(new BoardPosition(0, 1))).thenReturn(meleeEnemy);

        var availablePositions = List.of(new BoardPosition(0, 2), new BoardPosition(1, 2));
        var move = meleeEnemy.makeNextMove(); // go away
        assertNotNull(move);
        assertTrue(availablePositions.contains(move.boardPosition())); // attacked player
        assertEquals(move.move().getClass(), KingMove.class);
        meleeEnemy.changeMana(-1);

        move = meleeEnemy.makeNextMove();
        assertNull(move);
    }

    @Test
    void testAttackMove() {
        init();
        var rangeEnemy = new RangeEnemy(new BoardPosition(3, 3), smallBoard);
        assertEquals(rangeEnemy.getTeam(), CONST.ENEMY_TEAM);
        when(smallBoard.getCharacter(new BoardPosition(3, 3))).thenReturn(rangeEnemy);

        var move = rangeEnemy.makeNextMove();
        assertNotNull(move);
        assertEquals(move.boardPosition(), new BoardPosition(0, 0)); // attacked player
        assertEquals(move.move().getClass(), LongRangeMove.class);
        rangeEnemy.changeMana(-1);

        move = rangeEnemy.makeNextMove();
        assertNull(move);
    }
}
