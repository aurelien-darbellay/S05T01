package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)

public interface Action {

    default void addStrategyToTurn(PlayerStrategy playerStrategy) {
        playerStrategy.getActions().add(this);
    }
    boolean execute(Turn turn, Deque<PlayerStrategy> turnsToPlay, PlayerStrategy playerStrategy, BiFunction<Integer, List<Card>, List<Card>> biFunction);
}

