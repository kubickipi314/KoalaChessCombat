package com.io.service;

import com.io.core.board.SpecialCell;
import com.io.core.character.Character;
import com.io.core.moves.MoveDTO;

import java.util.List;
import java.util.Map;

public interface BoardInterface {
    boolean tryMakeMove(MoveDTO move);

    PlayerInterface getPlayer();

    Map<Integer, Integer> getTeamCount();

    boolean hasMoved();

    boolean hasAttacked();

    Character getAttacked();

    List<SpecialCell> getSpecialCells();

    public int getBoardWidth();

    public int getBoardHeight();

}
