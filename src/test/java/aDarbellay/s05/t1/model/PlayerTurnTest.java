package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.actions.Action;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void setIssuedFromDoubleSplit() {
    }

    @Test
    void setIssuedFromTripleSplit() {
    }
}