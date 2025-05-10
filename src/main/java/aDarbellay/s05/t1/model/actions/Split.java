package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.Player;

import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class Split implements Action {
    @Override
    public boolean execute(Turn turn, Deque<PlayerStrategy> turnsToPlay, PlayerStrategy playerStrategy, BiFunction<Integer, List<Card>, List<Card>> drawFunction) {
        addStrategyToTurn(playerStrategy);
        Hand newHand = splitHand(playerStrategy.getHand(), turn, drawFunction);
        PlayerStrategy newPLayerStrategy = createNewPlayerStrategy(turn, playerStrategy.getPlayer(), playerStrategy.getBet(), newHand);
        int indexStrategy = (int) turn.getPlayerStrategies().stream()
                .filter(strategy -> Objects.equals(strategy.getPlayer(), playerStrategy.getPlayer()))
                .count();
        newPLayerStrategy.setId(calculateNewId(turn, playerStrategy));
        turnsToPlay.push(newPLayerStrategy);
        turn.getPlayerStrategies().add(newPLayerStrategy);
        return false;
    }

    private Hand splitHand(Hand originalHand, Turn turn, BiFunction<Integer, List<Card>, List<Card>> drawFunction) {
        Card secondCard = originalHand.get(1);
        originalHand.remove(secondCard);
        List<Card> cardDrawnForFirstHand = drawFunction.apply(1, turn.getReserve());
        originalHand.addAll(cardDrawnForFirstHand);
        List<Card> cardDrawnForSecondHand = drawFunction.apply(1, turn.getReserve());
        cardDrawnForSecondHand.add(secondCard);
        return Hand.createPlayerHand(cardDrawnForSecondHand);
    }

    private PlayerStrategy createNewPlayerStrategy(Turn turn, Player player, Integer bet, Hand newHand) {
        PlayerStrategy newPLayerStrategy = new PlayerStrategy(turn.getId(), player);
        newPLayerStrategy.setBet(bet);
        newPLayerStrategy.setHand(newHand);
        return newPLayerStrategy;
    }

    private int calculateNewId(Turn turn, PlayerStrategy playerStrategy) {
        int indexStrategy = (int) turn.getPlayerStrategies().stream()
                .filter(strategy -> Objects.equals(strategy.getPlayer(), playerStrategy.getPlayer()))
                .count();
        return (-1) * playerStrategy.getId() * ((int) Math.pow(10, indexStrategy));
    }

    @Override
    public String toString() {
        return "Split{}";
    }
}
