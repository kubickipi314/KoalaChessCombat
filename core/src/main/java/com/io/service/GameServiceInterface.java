package com.io.service;

import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.board.SpecialCell;
import com.io.core.moves.Move;
import com.io.service.service_utils.CharacterRegister;
import com.io.service.service_utils.MoveResult;

import java.util.List;

public interface GameServiceInterface {
    List<CharacterRegister> getCharacterRegisters();

    List<Move> getPlayerMoves();

    boolean hasGameEnded();

    GameResult checkEndGameCondition();

    boolean isPlayersTurn();

    boolean movePlayer(BoardPosition boardPosition, Move move);

    void endPlayerTurn();

    boolean makeNextMove();

    MoveResult getLastMoveResult();


    List<BoardPosition> getAvailableTiles(Move move);


    int getPlayerHealth();

    int getPlayerMana();

    List<SpecialCell> getSpecialCells();

    int getRoomHeight();

    int getRoomWidth();
}
