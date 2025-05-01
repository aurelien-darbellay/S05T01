package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.model.*;
import aDarbellay.s05.t1.model.actions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DealerTest {

    static Game game;
    static Deck fullDeck;

    static class CautiousPLayer implements Player {
        @Override
        public String toString() {
            return "CautiousPLayer{}";
        }

        @Override
        public void placeBet(PlayerTurn playerTurn, int bet) {
            playerTurn.setBet(2);
        }

        @Override
        public Action pickAction(ActionType actionType) {
            return new Stand();
        }

        @Override
        public boolean isInteractive() {
            return false;
        }
    }

    static class RandomPlayer implements Player {

        @Override
        public void placeBet(PlayerTurn playerTurn, int bet) {
            playerTurn.setBet((int) Math.floor(Math.random() * 50));
        }

        @Override
        public Action pickAction(ActionType actionType) {
            return Math.random() < 0.5 ? new Stand() : new Hit();
        }

        @Override
        public boolean isInteractive() {
            return false;
        }

        @Override
        public String toString() {
            return "RandomPlayer{}";
        }
    }

    static class InteractivePlayer implements Player {

        @Override
        public void placeBet(PlayerTurn playerTurn, int quantity) {

        }

        @Override
        public Action pickAction(ActionType actionType) {
            return switch (actionType) {
                case HIT -> new Hit();
                case SPLIT -> new Split();
                case STAND -> new Stand();
                case DOUBLE -> new DoubleBet();
            };
        }

        @Override
        public boolean isInteractive() {
            return true;
        }

        @Override
        public String toString() {
            return "InteractivePlayer{}";
        }
    }

    @BeforeAll
    static void setUp() {
        game = new Game();
        fullDeck = new Deck();
        game.setPlayers(List.of(new CautiousPLayer(), new RandomPlayer()));
        game.setTurnsPlayed(new ArrayList<>());

    }

    @Test
    void startTurn() {
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, 10);
        System.out.println(newTurn.toString());
    }

    @Test
    void runSplitTurn() {
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, 10);
        System.out.println(newTurn.getTurnState().getValue());
        Turn finishedTurn = bob.playTurn(game, ActionType.STAND);
        System.out.println(finishedTurn.toString());
    }

    @Test
    void runSplitTurnWithInteractivePlayer() {
        game.setPlayers(List.of(new InteractivePlayer()));
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, 10);
        System.out.println(newTurn.getTurnState().getValue());
        Turn finishedTurn = bob.playTurn(game, ActionType.HIT);
        System.out.println(finishedTurn.toString());
    }
}