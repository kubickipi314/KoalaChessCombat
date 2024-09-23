package com.io.presenter.game;

import com.io.core.GameResult;
import com.io.core.board.BoardPosition;
import com.io.core.board.SpecialCell;
import com.io.presenter.game.components.MoveData;
import com.io.service.utils.CharacterRegister;
import com.io.service.utils.MoveResult;

import java.util.List;

public interface GameServiceInterface {
    List<CharacterRegister> getCharacterRegisters();

    List<MoveData> getPlayerMovesData();

    boolean hasGameEnded();

    GameResult checkEndGameCondition();

    boolean isPlayersTurn();

    boolean movePlayer(BoardPosition boardPosition, int moveNumber);

    void endPlayerTurn();

    boolean makeNextMove();

    MoveResult getLastMoveResult();


    List<BoardPosition> getAvailableTiles(int moveNumber);


    int getPlayerHealth();

    int getPlayerMana();

    List<SpecialCell> getSpecialCells();

    int getRoomHeight();

    int getRoomWidth();
}
