package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public class DoubleBet implements Action {
    private List<Card> drawnCard;

    @Override
    public boolean execute(Turn turn, Deque<PlayerStrategy> turnsToPlay, PlayerStrategy playerStrategy, BiFunction<Integer, List<Card>, List<Card>> biFunction) {
        addStrategyToTurn(playerStrategy);
        drawnCard = biFunction.apply(1, turn.getReserve());
        playerStrategy.getHand().addAll(drawnCard);
        playerStrategy.setBet(playerStrategy.getBet() * 2);
        return true;
    }

    @Override
    public String toString() {
        return "Double{" +
                "drawnCard=" + drawnCard +
                '}';
    }

}
