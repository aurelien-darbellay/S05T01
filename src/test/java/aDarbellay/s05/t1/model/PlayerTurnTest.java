package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.actions.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTurnTest {

    class TestPlayer implements Player {

        @Override
        public void placeBet(PlayerTurn playerTurn) {
        }

        @Override
        public Action pickAction() {
            return null;
        }
    }

    @Test
    void setIssuedFromSplit() {
        PlayerTurn newPT = new PlayerTurn(1, new TestPlayer());
        newPT.setIssuedFromTripleSplit(true);
        assertTrue(newPT.isIssuedFromSplit());
        assertTrue(newPT.isIssuedFromDoubleSplit());
        assertTrue(newPT.isIssuedFromTripleSplit());
        newPT.setIssuedFromTripleSplit(false);
        assertTrue(newPT.isIssuedFromSplit());
        assertTrue(newPT.isIssuedFromDoubleSplit());
        assertFalse(newPT.isIssuedFromTripleSplit());
        newPT.setIssuedFromDoubleSplit(false);
        assertTrue(newPT.isIssuedFromSplit());
        assertFalse(newPT.isIssuedFromDoubleSplit());
        assertFalse(newPT.isIssuedFromTripleSplit());
        newPT.setIssuedFromSplit(false);
        assertFalse(newPT.isIssuedFromSplit());
        assertFalse(newPT.isIssuedFromDoubleSplit());
        assertFalse(newPT.isIssuedFromTripleSplit());
    }

    @Test
    void setIssuedFromDoubleSplit() {
    }

    @Test
    void setIssuedFromTripleSplit() {
    }
}