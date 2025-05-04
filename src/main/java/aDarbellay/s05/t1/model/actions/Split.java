package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.hands.Hand;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public class Split implements Action {
    @Override
    public boolean execute(Turn turn, Deque<PlayerStrategy> turnsToPlay, PlayerStrategy playerStrategy, BiFunction<Integer, List<Card>, List<Card>> drawFunction) {
        addStrategyToTurn(playerStrategy);
        Hand newHand = splitHand(playerStrategy.getHand(), turn, drawFunction);
        PlayerStrategy newPLayerStrategy = createNewPlayerStrategy(turn, playerStrategy, newHand);
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

    private PlayerStrategy createNewPlayerStrategy(Turn turn, PlayerStrategy playerStrategy, Hand newHand) {
        PlayerStrategy newPLayerStrategy = new PlayerStrategy(turn.getId(), playerStrategy.getPlayer());
        newPLayerStrategy.setBet(playerStrategy.getBet());
        newPLayerStrategy.setHand(newHand);
        return newPLayerStrategy;
    }
}
