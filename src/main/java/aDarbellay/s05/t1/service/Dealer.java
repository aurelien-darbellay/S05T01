package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.model.Player;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.actions.DoubleBet;
import aDarbellay.s05.t1.model.actions.Stand;
import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.hands.Hand.Visibility;
import aDarbellay.s05.t1.validation.DealingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Dealer {

    private Deck fullDeck;
    final private DealingValidation dealingValidation;

    @Autowired
    public Dealer(Deck fullDeck, DealingValidation dealingValidation) {
        this.fullDeck = fullDeck;
        this.dealingValidation = dealingValidation;
    }

    public Turn startTurn(Game game, int bet, int playerId) {
        //Should I validate that there isn't already a turn going on//
        Turn newTurn = initializeNewTurn(game);
        game.setActiveTurn(newTurn);
        takeBets(newTurn, bet, playerId);
        distributeCard(newTurn);
        newTurn.setTurnState(Turn.TurnState.HANDS_DISTRIBUTED);
        return newTurn;
    }

    private Turn initializeNewTurn(Game game) {
        Turn newTurn = new Turn(game.getTurnsPlayed().size() + 1);
        newTurn.setGameId(game.getId());
        List<PlayerStrategy> playerStrategies = createPlayerTurns(newTurn, game.getPlayers());
        newTurn.setPlayerStrategies(playerStrategies);
        return newTurn;
    }

    private List<PlayerStrategy> createPlayerTurns(Turn turn, List<Player> players) {
        return players.stream().map(player -> new PlayerStrategy(turn.getId(), player)).toList();
    }

    private void takeBets(Turn turn, Integer bet, int playerId) {
        turn.getPlayerStrategies().forEach(playerTurn ->
        {
            if (!playerTurn.getPlayer().isInteractive()) playerTurn.getPlayer().placeBet(playerTurn, null);
            else if (playerTurn.getPlayer().getId() == playerId) playerTurn.getPlayer().placeBet(playerTurn, bet);
        });
    }

    private void distributeCard(Turn turn) {
        Collections.shuffle(fullDeck);
        turn.setReserve(fullDeck);
        turn.getPlayerStrategies().forEach(playerTurn -> {
            playerTurn.setHand(Hand.createPlayerHand(getNCardFromReserve(2, turn.getReserve())));
        });
        turn.setDealerHand(Hand.createDealerHand(getNCardFromReserve(2, turn.getReserve())));
    }

    private List<Card> getNCardFromReserve(int n, List<Card> reserve) {
        List<Card> drawnCards = new ArrayList<>(reserve.subList(0, n));
        reserve.subList(0, n).clear(); // remove them from deck
        return drawnCards;
    }

    public Turn playTurn(Game game, ActionType actionType, int playerId) {
        Turn activeTurn = game.getActiveTurn();
        invitePlayersToPlayHand(activeTurn, actionType, playerId);
        if (activeTurn.getTurnState().equals(Turn.TurnState.HANDS_PLAYED)) {
            revealDealerHand(activeTurn);
            calculateResults(activeTurn);
            activeTurn.setTurnState(Turn.TurnState.TURN_FINISHED);
            game.setActiveTurn(null);
        }
        return activeTurn;
    }

    private void invitePlayersToPlayHand(Turn turn, ActionType actionType, int playerId) {
        Deque<PlayerStrategy> playsToRegister = new ArrayDeque<>(turn.getPlayerStrategies());

        while (!playsToRegister.isEmpty()) {
            PlayerStrategy playerStrategy = playsToRegister.pop();
            if (isActionRequired(playerStrategy)) {
                registerAction(turn, playerStrategy, playsToRegister, actionType, playerId);
                if (isInputRequired(turn)) return;
            }
        }
        turn.setTurnState(Turn.TurnState.HANDS_PLAYED);
    }

    private void registerAction(Turn turn, PlayerStrategy playerStrategy, Deque<PlayerStrategy> turnsToPlay, ActionType actionType, int playerId) {
        Action playerAction;
        Player player = playerStrategy.getPlayer();
        do {
            if (player.getId() == playerId) playerAction = player.pickAction(actionType);
            else playerAction = player.pickAction(null);
            dealingValidation.validateAction(playerAction, playerStrategy);
            playerAction.execute(turn, turnsToPlay, playerStrategy, this::getNCardFromReserve);
            if (isInputRequired(playerStrategy)) {
                turn.setTurnState(Turn.TurnState.ONHOLD);
                return;
            }
        } while (isActionRequired(playerStrategy));
    }

    private boolean isActionRequired(PlayerStrategy playerStrategy) {
        return (playerStrategy.getHand().getHandValue() < 21
                && playerStrategy.getActions().stream().noneMatch(a -> a instanceof Stand || a instanceof DoubleBet)
        );
    }

    private boolean isInputRequired(Turn turn) {
        return turn.getTurnState().equals(Turn.TurnState.ONHOLD);
    }

    private boolean isInputRequired(PlayerStrategy playerStrategy) {
        return isActionRequired(playerStrategy) && playerStrategy.getPlayer().isInteractive();
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
        turn.getPlayerStrategies().forEach(playerTurn -> {
            int playerPoints = playerTurn.getHand().getHandValue();
            if (playerPoints > 21) playerTurn.setResult(PlayerStrategy.ResultType.LOSS);
            else if (dealerPoints > 21) playerTurn.setResult(PlayerStrategy.ResultType.WIN);
            else {
                setResultIfNoneBust(playerPoints, dealerPoints, playerTurn, turn);
            }
        });
    }

    private void setResultIfNoneBust(int playerPoints, int dealerPoints, PlayerStrategy playerStrategy, Turn turn) {
        switch (Integer.signum(playerPoints - dealerPoints)) {
            case 1:
                playerStrategy.setResult(PlayerStrategy.ResultType.WIN);
                break;
            case -1:
                playerStrategy.setResult(PlayerStrategy.ResultType.LOSS);
                break;
            case 0:
                setResultByHandSize(playerStrategy, turn.getDealerHand());
                break;
        }
    }

    private void setResultByHandSize(PlayerStrategy playerStrategy, Hand dealerHand) {
        if (playerStrategy.getHand().size() == 2) {
            if (dealerHand.size() == 2) playerStrategy.setResult(PlayerStrategy.ResultType.PUSH);
            else playerStrategy.setResult(PlayerStrategy.ResultType.WIN);
        } else if (dealerHand.size() == 2) playerStrategy.setResult(PlayerStrategy.ResultType.LOSS);
        else playerStrategy.setResult(PlayerStrategy.ResultType.PUSH);
    }

    public List<Card> getFullDeck() {
        return fullDeck;
    }

    public void setFullDeck(Deck fullDeck) {
        this.fullDeck = fullDeck;
    }

}
