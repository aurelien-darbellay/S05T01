package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.exception.UntimelyActionException;
import aDarbellay.s05.t1.model.Bet;
import aDarbellay.s05.t1.model.Player;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionChoice;
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

    public Turn startTurn(Game game, Bet bet, int strategyId) throws EntityNotFoundException {
        //Should I validate that there isn't already a turn going on//
        Turn newTurn = initializeNewTurn(game);
        takeBets(newTurn, bet, strategyId);
        if (newTurn.getTurnState() == Turn.TurnState.BETS_PLACED) {
            distributeCard(newTurn);
            newTurn.setTurnState(Turn.TurnState.HANDS_DISTRIBUTED);
        }
        return newTurn;
    }

    private Turn initializeNewTurn(Game game) {
        if (game.getActiveTurn() != null) return game.getActiveTurn();
        Turn newTurn = new Turn(game.getTurnsPlayed().size() + 1);
        newTurn.setGameId(game.getId());
        List<PlayerStrategy> playerStrategies = createPlayerStrategies(newTurn, game.getPlayers());
        newTurn.setPlayerStrategies(playerStrategies);
        game.setActiveTurn(newTurn);
        return newTurn;
    }

    private List<PlayerStrategy> createPlayerStrategies(Turn turn, List<Player> players) {
        return players.stream().map(player -> new PlayerStrategy(turn.getId(), player)).toList();
    }

    private void takeBets(Turn turn, Bet bet, int strategyId) throws EntityNotFoundException {
        turn.getPlayerStrategies().stream()
                .filter(playerStrategy -> playerStrategy.getBet() == null)
                .forEach(playerStrategy -> {
                    if (playerStrategy.getPlayer().isInteractive()) setBetInStrategy(playerStrategy, strategyId, bet);
                    else setBetInStrategy(playerStrategy);
                });
        if (!bet.isUsed() && hasInteractivePlayer(turn))
            throw new EntityNotFoundException(PlayerStrategy.class, String.valueOf(strategyId));
        if (turn.getPlayerStrategies().stream()
                .filter(playerStrategy -> playerStrategy.getBet() == null)
                .toList().isEmpty())
            turn.setTurnState(Turn.TurnState.BETS_PLACED);
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

    public Turn playTurn(Game game, ActionChoice actionChoice, int strategyId) throws EntityNotFoundException, UntimelyActionException {
        Turn activeTurn = game.getActiveTurn();
        if (isNotReadyToPlayHands(game)) throw new UntimelyActionException(Action.class);
        activeTurn.setTurnState(Turn.TurnState.PLAYERS_CHOOSE_STRATEGY);
        invitePlayersToPlayHand(activeTurn, actionChoice, strategyId);
        if (activeTurn.getTurnState().equals(Turn.TurnState.HANDS_PLAYED)) {
            revealDealerHand(activeTurn);
            calculateResults(activeTurn);
            activeTurn.setTurnState(Turn.TurnState.TURN_FINISHED);
            game.setActiveTurn(null);
        }
        return activeTurn;
    }

    private void invitePlayersToPlayHand(Turn turn, ActionChoice actionChoice, int strategyId) throws EntityNotFoundException {

        Deque<PlayerStrategy> playsToRegister = new ArrayDeque<>(turn.getPlayerStrategies().stream().filter(this::isActionRequired).toList());
        while (!playsToRegister.isEmpty()) {
            PlayerStrategy playerStrategy = playsToRegister.pop();
            registerAction(turn, playerStrategy, playsToRegister, actionChoice, strategyId);
        }
        if (!actionChoice.isUsed() && hasInteractivePlayer(turn))
            throw new EntityNotFoundException(PlayerStrategy.class, String.valueOf(strategyId));
        if (turn.getPlayerStrategies().stream().noneMatch(this::isActionRequired))
            turn.setTurnState(Turn.TurnState.HANDS_PLAYED);
    }

    private void registerAction(Turn turn, PlayerStrategy playerStrategy, Deque<PlayerStrategy> turnsToPlay, ActionChoice actionChoice, int strategyId) throws EntityNotFoundException {
        Player player = playerStrategy.getPlayer();
        if (player.isAutomatic()) registerAutomaticAction(player, playerStrategy, turn, turnsToPlay);
        else if (player.isInteractive()) {
            registerInteractiveAction(player, actionChoice, playerStrategy, strategyId, turn, turnsToPlay);
            if (isActionRequired(playerStrategy)) turn.setInputRequired(true);
        }
    }

    private void registerAutomaticAction(Player player, PlayerStrategy playerStrategy, Turn turn, Deque<PlayerStrategy> turnsToPlay) {
        do {
            Action playerAction = player.pickAction(null);
            dealingValidation.validateAction(playerAction, playerStrategy);
            playerAction.execute(turn, turnsToPlay, playerStrategy, this::getNCardFromReserve);
        } while (isActionRequired(playerStrategy));
    }

    private void registerInteractiveAction(Player player, ActionChoice actionChoice, PlayerStrategy playerStrategy, int strategyId, Turn turn, Deque<PlayerStrategy> turnsToPlay) throws EntityNotFoundException {
        if (playerStrategy.getId() == strategyId && !actionChoice.isUsed()) {
            Action playerAction = player.pickAction(actionChoice.getActionType());
            dealingValidation.validateAction(playerAction, playerStrategy);
            playerAction.execute(turn, turnsToPlay, playerStrategy, this::getNCardFromReserve);
            actionChoice.setUsed(true);
        }
    }

    private boolean isNotReadyToPlayHands(Game game) {
        return game.getActiveTurn() == null || !List.of(Turn.TurnState.HANDS_DISTRIBUTED, Turn.TurnState.PLAYERS_CHOOSE_STRATEGY).contains(game.getActiveTurn().getTurnState());
    }

    private boolean hasInteractivePlayer(Turn turn) {
        return turn.getPlayerStrategies().stream().anyMatch(playerStrategy -> playerStrategy.getPlayer().isInteractive());
    }

    private boolean isActionRequired(PlayerStrategy playerStrategy) {
        return (playerStrategy.getHand().getHandValue() < 21
                && playerStrategy.getActions().stream().noneMatch(a -> a instanceof Stand || a instanceof DoubleBet)
        );
    }


    private void setBetInStrategy(PlayerStrategy strategy) {
        strategy.getPlayer().placeBet(strategy, null);
    }

    private void setBetInStrategy(PlayerStrategy strategy, int strategyId, Bet bet) {
        if (bet.isUsed()) {
            return;
        }
        if (strategy.getId() == strategyId) {
            strategy.getPlayer().placeBet(strategy, bet.getValue());
            bet.setUsed(true);
        }
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
