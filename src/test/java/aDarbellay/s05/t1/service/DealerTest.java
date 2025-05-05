package aDarbellay.s05.t1.service;


import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.exception.UntimelyActionException;
import aDarbellay.s05.t1.model.Bet;
import aDarbellay.s05.t1.model.Player;
import aDarbellay.s05.t1.model.actions.ActionChoice;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.validation.DealingValidation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import testClasses.CautiousPlayer;
import testClasses.InteractivePlayer;
import testClasses.RandomPlayer;

import java.util.ArrayList;
import java.util.List;

class DealerTest {

    static Game game;
    static Deck fullDeck;
    static List<Player> automaticPlayers;
    static List<Player> oneInteractivePlayer;
    static List<Player> interactivePlayers;
    static List<Player> mixedPlayers;


    @BeforeAll
    static void setUp() {
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
        automaticPlayers = List.of(cautiousPlayer,randomPlayer);
        oneInteractivePlayer = List.of(interactivePlayer);
        interactivePlayers = List.of(interactivePlayer,secondInteractivePlayer);
        mixedPlayers = List.of(cautiousPlayer,interactivePlayer,randomPlayer,secondInteractivePlayer);
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
        assertThrows(EntityNotFoundException.class,()->bob.startTurn(game, new Bet(10), 1));
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
    void runSplitTurnWithAutomaticPlayers() throws EntityNotFoundException, UntimelyActionException {
        game.setPlayers(automaticPlayers);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(0), 0);
        System.out.println(newTurn.getTurnState().getValue());
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.STAND);
        Turn finishedTurn = bob.playTurn(game, actionChoice,0);
        System.out.println(finishedTurn.toString());
    }

    @Test
    void runSplitTurnWithOneInteractivePlayer() throws EntityNotFoundException, UntimelyActionException {
        game.setPlayers(oneInteractivePlayer);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        System.out.println(newTurn.getTurnState().getValue());
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.HIT);
        Turn nextStep = bob.playTurn(game, actionChoice, 3);
        System.out.println(nextStep.toString());
        ActionChoice anotherChoice = new ActionChoice();
        anotherChoice.setActionType(ActionType.STAND);
        Turn turnDone = bob.playTurn(game, anotherChoice, 3);
        System.out.println(turnDone.toString());
    }

    @Test
    void runTurnWithMixedPlayers() throws EntityNotFoundException, UntimelyActionException {
        game.setPlayers(mixedPlayers);
        Dealer bob = new Dealer(fullDeck, new DealingValidation());
        Turn newTurn = bob.startTurn(game, new Bet(10), 3);
        System.out.println(newTurn.getTurnState().getValue());
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.HIT);
        assertThrows(UntimelyActionException.class, () -> bob.playTurn(game, actionChoice, 3)) ;
    }

}