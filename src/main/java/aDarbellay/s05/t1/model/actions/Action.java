package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.Card;
import aDarbellay.s05.t1.model.PlayerTurn;
import aDarbellay.s05.t1.model.Turn;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public interface Action {
    boolean execute(Turn turn, List<Card> reserve, Deque<PlayerTurn> turnsToPlay, PlayerTurn playerTurn, BiFunction<Integer, List<Card>, List<Card>> biFunction);
}
