package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.model.*;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.actions.DoubleBet;
import aDarbellay.s05.t1.model.actions.Stand;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.hands.Hand.Visibility;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Dealer {

    private boolean gameOn;
    private Deck fullDeck;
    final private DealingValidation dealingValidation;

    public Dealer(Deck fullDeck, DealingValidation dealingValidation) {
        this.gameOn = false;
        this.fullDeck = fullDeck;
        this.dealingValidation = dealingValidation;
    }

    public Turn startTurn(Game game, int bet) {
        //Should I validate that there isn't already a turn going on//
        setGameOn(true);
        Turn newTurn = initializeNewTurn(game);
        game.setActiveTurn(newTurn);
        takeBets(newTurn, bet);
        distributeCard(newTurn);
        newTurn.setTurnState(Turn.TurnState.HANDS_DISTRIBUTED);
        return newTurn;
    }

    private Turn initializeNewTurn(Game game) {
        Turn newTurn = new Turn(game.getTurnsPlayed().size() + 1);
        List<PlayerTurn> playerTurns = createPlayerTurns(newTurn, game.getPlayers());
        newTurn.setPlayerTurns(playerTurns);
        return newTurn;
    }

    private List<PlayerTurn> createPlayerTurns(Turn turn, List<Player> players) {
        return players.stream().map(player -> new PlayerTurn(turn.getId(), player)).toList();
    }

    private void takeBets(Turn turn, int bet) {
        turn.getPlayerTurns().forEach(playerTurn -> playerTurn.getPlayer().placeBet(playerTurn, bet));
    }

    private void distributeCard(Turn turn) {
        Collections.shuffle(fullDeck);
        turn.setReserve(fullDeck);
        turn.getPlayerTurns().forEach(playerTurn -> {
            playerTurn.setHand(Hand.createPlayerHand(getNCardFromReserve(2, turn.getReserve())));
        });
        turn.setDealerHand(Hand.createDealerHand(getNCardFromReserve(2, turn.getReserve())));
    }

    private List<Card> getNCardFromReserve(int n, List<Card> reserve) {
        List<Card> drawnCards = new ArrayList<>(reserve.subList(0, n));
        reserve.subList(0, n).clear(); // remove them from deck
        return drawnCards;
    }


    public Turn playTurn(Game game, ActionType actionType) {
        Turn activeTurn = game.getActiveTurn();
        invitePlayersToPlayHand(activeTurn, actionType);
        if (activeTurn.getTurnState().equals(Turn.TurnState.HANDS_PLAYED)) {
            revealDealerHand(activeTurn);
            calculateResults(activeTurn);
            setGameOn(false);
            activeTurn.setTurnState(Turn.TurnState.TURN_FINISHED);
        }
        return activeTurn;
    }


    private void invitePlayersToPlayHand(Turn turn, ActionType actionType) {
        Deque<PlayerTurn> playsToRegister = new ArrayDeque<>(turn.getPlayerTurns());

        while (!playsToRegister.isEmpty()) {
            PlayerTurn playerTurn = playsToRegister.pop();
            if (isActionRequired(playerTurn)) {
                registerPlay(turn, playerTurn, playsToRegister, actionType);
                if (isInputRequired(turn)) return;
            }
        }
        turn.setTurnState(Turn.TurnState.HANDS_PLAYED);
    }

    private void registerPlay(Turn turn, PlayerTurn playerTurn, Deque<PlayerTurn> turnsToPlay, ActionType actionType) {
        Action playerAction;
        Player player = playerTurn.getPlayer();
        do {
            playerAction = player.pickAction(actionType);
            dealingValidation.validateAction(playerAction, playerTurn);
            playerAction.execute(turn, turnsToPlay, playerTurn, this::getNCardFromReserve);
            if (isInputRequired(playerTurn)) {
                turn.setTurnState(Turn.TurnState.ONHOLD);
                return;
            }
        } while (isActionRequired(playerTurn));
    }

    private boolean isActionRequired(PlayerTurn playerTurn) {
        return (playerTurn.getHand().getHandValue() < 21
                && playerTurn.getActions().stream().noneMatch(a -> a instanceof Stand || a instanceof DoubleBet)
        );
    }

    private boolean isInputRequired(Turn turn) {
        return turn.getTurnState().equals(Turn.TurnState.ONHOLD);
    }

    private boolean isInputRequired(PlayerTurn playerTurn) {
        return isActionRequired(playerTurn) && playerTurn.getPlayer().isInteractive();
    }


    private void revealDealerHand(Turn turn) {
        Hand dealerHand = turn.getDealerHand();
        dealerHand.setVisibility(Visibility.COMPLETE);
        while (dealerHand.getHandValue() < 17) {
            Card newCard = getNCardFromReserve(1, turn.getReserve()).getFirst();
            dealerHand.add(newCard);
        }
    }

    private void calculateResults(Turn turn) {
        int dealerPoints = turn.getDealerHand().getHandValue();
        turn.getPlayerTurns().forEach(playerTurn -> {
            int playerPoints = playerTurn.getHand().getHandValue();
            if (playerPoints > 21) playerTurn.setResult(PlayerTurn.ResultType.LOSS);
            else if (dealerPoints > 21) playerTurn.setResult(PlayerTurn.ResultType.WIN);
            else {
                setResultIfNoneBust(playerPoints, dealerPoints, playerTurn, turn);
            }
        });
    }

    private void setResultIfNoneBust(int playerPoints, int dealerPoints, PlayerTurn playerTurn, Turn turn) {
        switch (Integer.signum(playerPoints - dealerPoints)) {
            case 1:
                playerTurn.setResult(PlayerTurn.ResultType.WIN);
                break;
            case -1:
                playerTurn.setResult(PlayerTurn.ResultType.LOSS);
                break;
            case 0:
                setResultByHandSize(playerTurn, turn.getDealerHand());
                break;
        }
    }

    private void setResultByHandSize(PlayerTurn playerTurn, Hand dealerHand) {
        if (playerTurn.getHand().size() == 2) {
            if (dealerHand.size() == 2) playerTurn.setResult(PlayerTurn.ResultType.PUSH);
            else playerTurn.setResult(PlayerTurn.ResultType.WIN);
        } else if (dealerHand.size() == 2) playerTurn.setResult(PlayerTurn.ResultType.LOSS);
        else playerTurn.setResult(PlayerTurn.ResultType.PUSH);
    }


    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public List<Card> getFullDeck() {
        return fullDeck;
    }

    public void setFullDeck(Deck fullDeck) {
        this.fullDeck = fullDeck;
    }

}
