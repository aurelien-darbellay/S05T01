package aDarbellay.s05.t1.service;


import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import testClasses.CautiousPlayer;
import testClasses.InteractivePlayer;
import testClasses.RandomPlayer;

import java.util.ArrayList;
import java.util.List;

class DealerTest {

    static Game game;
    static Deck fullDeck;

    @BeforeAll
    static void setUp() {
        game = new Game();
        fullDeck = new Deck();
        game.setPlayers(List.of(new CautiousPlayer(), new RandomPlayer()));
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