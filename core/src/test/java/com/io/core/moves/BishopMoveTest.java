package com.io.core.moves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BishopMoveTest {

    @Test
    void isMoveValid() {

    }

    @Test
    void getAccessibleCells() {
    }

    @Test
    void getType() {
        BishopMove bishopMove = new BishopMove(10, 5);

        var type = bishopMove.getType();

        assertEquals(type, MoveType.BISHOP, "The type should match the value of BISHOP enum");
    }

    @Test
    public void testGetCost() {
        BishopMove bishopMove = new BishopMove(10, 5);

        int cost = bishopMove.getCost();

        assertEquals(10, cost, "The cost should match the value set in the constructor.");
    }

    @Test
    public void testGetDamage() {
        BishopMove bishopMove = new BishopMove(10, 5);

        int damage = bishopMove.getDamage();

        assertEquals(5, damage, "The damage should match the value set in the constructor.");
    }
}