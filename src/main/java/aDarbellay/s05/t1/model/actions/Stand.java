package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public class Stand implements Action{

    @Override
    public boolean execute(Turn turn, Deque<PlayerStrategy> turnsToPlay, PlayerStrategy playerStrategy, BiFunction<Integer, List<Card>, List<Card>> biFunction) {
        addStrategyToTurn(playerStrategy);
        return true;
    }

    @Override
    public String toString() {
        return "Stand{}";
    }
}
