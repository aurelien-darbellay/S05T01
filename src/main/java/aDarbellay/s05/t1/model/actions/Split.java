package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.Card;
import aDarbellay.s05.t1.model.PlayerTurn;
import aDarbellay.s05.t1.model.Turn;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.hands.Hand.HandType;
import aDarbellay.s05.t1.model.hands.Hand.Visibility;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public class Split implements Action {
    @Override
    public boolean execute(Turn turn, List<Card> reserve, Deque<PlayerTurn> turnsToPlay, PlayerTurn playerTurn, BiFunction<Integer, List<Card>, List<Card>> biFunction) {
        Card secondCard = playerTurn.getHand().get(1);
        playerTurn.getHand().remove(secondCard);
        List<Card> cardDrawnForFirstHand = biFunction.apply(1, turn.getReserve());
        playerTurn.getHand().addAll(cardDrawnForFirstHand);
        PlayerTurn newPLayerTurn = new PlayerTurn(turn.getId(), playerTurn.getPlayer());
        newPLayerTurn.setBet(playerTurn.getBet());
        List<Card> cardDrawnForSecondHand = biFunction.apply(1, turn.getReserve());
        cardDrawnForSecondHand.add(secondCard);
        newPLayerTurn.setHand(cardDrawnForSecondHand, Hand.createHand(Visibility.COMPLETE, HandType.OWN));
        turnsToPlay.push(newPLayerTurn);
        turn.getPlayerTurns().add(newPLayerTurn);
        return false;
    }
}
