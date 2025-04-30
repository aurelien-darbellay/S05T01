package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.Card;
import aDarbellay.s05.t1.model.PlayerTurn;

import java.util.List;
import java.util.function.BiFunction;

public class Hit implements Action {
    private List<Card> drawnCard;

    @Override
    public boolean execute(PlayerTurn playerTurn, List<Card> reserve, BiFunction<Integer, List<Card>, List<Card>> biFunction) {
        drawnCard = biFunction.apply(1, reserve);
        playerTurn.getHand().addAll(drawnCard);
        playerTurn.getActions().add(this);
        return false;
    }
}
