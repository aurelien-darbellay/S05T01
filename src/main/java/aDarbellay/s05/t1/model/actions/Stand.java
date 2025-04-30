package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.Card;
import aDarbellay.s05.t1.model.PlayerTurn;

import java.util.List;
import java.util.function.BiFunction;

public class Stand implements Action {

    @Override
    public boolean execute(PlayerTurn playerTurn, List<Card> reserve, BiFunction<Integer, List<Card>, List<Card>> biFunction) {
        playerTurn.getActions().add(this);
        return true;
    }
}
