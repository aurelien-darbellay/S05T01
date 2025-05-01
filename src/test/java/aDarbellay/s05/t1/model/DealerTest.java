package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.Stand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DealerTest {

    static Game game;
    static List<Card> fullDeck;

    static class CautiousPLayer implements Player {
        @Override
        public String toString() {
            return "CautiousPLayer{}";
        }

        @Override
        public void placeBet(PlayerTurn playerTurn) {
            playerTurn.setBet(2);
        }

        @Override
        public Action pickAction() {
            return new Stand();
        }
    }

    @BeforeAll
    static void setUp() {
        game = new Game();
        fullDeck = new Deck();
        game.setPlayers(List.of(new CautiousPLayer()));
        game.setTurnsPlayed(new ArrayList<>());

    }

    @Test
    void runTurn() {
        Dealer bob = new Dealer(game, fullDeck);
        bob.runTurn();
        System.out.println(game.toString());

    }
}