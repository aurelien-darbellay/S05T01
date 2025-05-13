package aDarbellay.s05.t1.service;


import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.exception.IllegalBetException;
import aDarbellay.s05.t1.exception.UntimelyActionException;
import aDarbellay.s05.t1.model.Bet;
import aDarbellay.s05.t1.model.actions.ActionChoice;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.CautiousPlayer;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.RandomPlayer;
import aDarbellay.s05.t1.service.game.Dealer;
import aDarbellay.s05.t1.validation.DealingValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testClasses.InteractivePlayer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DealerTest {

    static Game game;
    static Deck fullDeck;
    static List<Player> automaticPlayers;
    static List<Player> oneInteractivePlayer;
    static List<Player> interactivePlayers;
    static List<Player> mixedPlayers;


    @BeforeEach
    void setUp() {
        game = new Game();
        fullDeck = new Deck();
        Player cautiousPlayer = new CautiousPlayer();
        cautiousPlayer.setId(1);
        Player randomPlayer = new RandomPlayer();
        randomPlayer.setId(2);
        Player interactivePlayer = new InteractivePlayer();
        interactivePlayer.setId(3);
        Player secondInteractivePlayer = new InteractivePlayer();
        secondInteractivePlayer.setId(4);
        automaticPlayers = List.of(cautiousPlayer, randomPlayer);
        oneInteractivePlayer = List.of(interactivePlayer);
        interactivePlayers = List.of(interactivePlayer, secondInteractivePlayer);
        mixedPlayers = List.of(cautiousPlayer, interactivePlayer, randomPlayer, secondInteractivePlayer);
        game.setTurnsPlayed(new ArrayList<>());

    }

    @Test
    void startTurn() throws EntityNotFoundException {
        game.setPlayers(automaticPlayers);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 0);
        System.out.println(newTurn.toString());
    }

    @Test
    void startThrowExceptionWithOneInteractivePLayer() throws EntityNotFoundException {
        game.setPlayers(oneInteractivePlayer);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        assertThrows(EntityNotFoundException.class, () -> bob.startTurn(game, new Bet(10), 1));
    }

    @Test
    void startWithOneInteractivePLayer() throws EntityNotFoundException {
        game.setPlayers(oneInteractivePlayer);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        System.out.println(newTurn.toString());
    }

    @Test
    void startWithMixedPlayers() throws EntityNotFoundException {
        game.setPlayers(mixedPlayers);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        System.out.println(newTurn.toString());
    }

    @Test
    void startWithMixedPlayersFinishBets() throws EntityNotFoundException {
        game.setPlayers(mixedPlayers);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        System.out.println(newTurn.toString());
        Turn moveforward = bob.startTurn(game, new Bet(12), 4);
        System.out.println(moveforward);
    }

    @Test
    void runSplitTurnWithAutomaticPlayers() throws EntityNotFoundException, UntimelyActionException, IllegalActionException, IllegalBetException {
        game.setPlayers(automaticPlayers);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(0), 0);
        System.out.println(newTurn.getTurnState().getValue());
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.STAND);
        Turn finishedTurn = bob.playTurn(game, actionChoice, 0);
        System.out.println(finishedTurn.toString());
    }

    @Test
    void runSplitTurnWithOneInteractivePlayer() throws EntityNotFoundException, UntimelyActionException, IllegalActionException, IllegalBetException {
        game.setPlayers(oneInteractivePlayer);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        System.out.println(newTurn.getTurnState().getValue());
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.STAND);
        Turn nextStep = bob.playTurn(game, actionChoice, 3);
        System.out.println(nextStep.toString());
    }

    @Test
    void runTurnWithMixedPlayers() throws EntityNotFoundException, UntimelyActionException, IllegalActionException, IllegalBetException {
        game.setPlayers(mixedPlayers);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        bob.startTurn(game, new Bet(12), 4);
        System.out.println(game.getActiveTurn().toString());
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.STAND);
        System.out.println(bob.playTurn(game, actionChoice, 3).toString());
        ActionChoice anotherChoice = new ActionChoice();
        anotherChoice.setActionType(ActionType.HIT);
        System.out.println(bob.playTurn(game, anotherChoice, 4).toString());
    }

    @Test
    void runWithSplit() throws EntityNotFoundException, UntimelyActionException, IllegalActionException, IllegalBetException {
        game.setPlayers(oneInteractivePlayer);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        PlayerStrategy strategy = newTurn.getPlayerStrategies().getFirst();
        Hand hand = strategy.getHand();
        Card secondCard = hand.get(1);
        hand.remove(secondCard);
        newTurn.getReserve().add(secondCard);
        Card firstCardTwin = newTurn.getReserve().stream().filter(card -> card.getValue().substring(1).equals(hand.getFirst().getValue().substring(1))).findFirst().orElse(null);
        hand.add(firstCardTwin);
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.SPLIT);
        System.out.println(bob.playTurn(game, actionChoice, 3).toString());
        ActionChoice anotherChoice = new ActionChoice();
        anotherChoice.setActionType(ActionType.STAND);
        System.out.println(bob.playTurn(game, anotherChoice, 3).toString());
        ActionChoice yetAnotherChoice = new ActionChoice();
        yetAnotherChoice.setActionType(ActionType.DOUBLE);
        System.out.println(bob.playTurn(game, yetAnotherChoice, -30).toString());
    }
}