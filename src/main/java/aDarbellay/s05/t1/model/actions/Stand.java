package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.PlayerTurn;
import aDarbellay.s05.t1.model.games.Turn;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public class Stand implements Action {

    @Override
    public boolean execute(Turn turn, Deque<PlayerTurn> turnsToPlay, PlayerTurn playerTurn, BiFunction<Integer, List<Card>, List<Card>> biFunction) {
        addActionToTurn(playerTurn);
        return true;
    }

    @Override
    public String toString() {
        return "Stand{}";
    }
}
