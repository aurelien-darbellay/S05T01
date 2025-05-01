package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.Card;
import aDarbellay.s05.t1.model.PlayerTurn;
import aDarbellay.s05.t1.model.Turn;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public class Hit implements Action {
    private List<Card> drawnCard;

    @Override
    public boolean execute(Turn turn, Deque<PlayerTurn> turnsToPlay, PlayerTurn playerTurn, BiFunction<Integer, List<Card>, List<Card>> biFunction) {
        addActionToTurn(playerTurn);
        drawnCard = biFunction.apply(1, turn.getReserve());
        playerTurn.getHand().addAll(drawnCard);
        return false;
    }

}
