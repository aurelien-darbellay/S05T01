package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.Card;
import aDarbellay.s05.t1.model.PlayerTurn;
import aDarbellay.s05.t1.model.Turn;
import aDarbellay.s05.t1.model.hands.Hand;

import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

public class Split implements Action {
    @Override
    public boolean execute(Turn turn, Deque<PlayerTurn> turnsToPlay, PlayerTurn playerTurn, BiFunction<Integer, List<Card>, List<Card>> drawFunction) {
        addActionToTurn(playerTurn);
        Hand newHand = splitHand(playerTurn.getHand(), turn, drawFunction);
        PlayerTurn newPLayerTurn = createNewPlayerTurn(turn, playerTurn, newHand);
        turnsToPlay.push(newPLayerTurn);
        turn.getPlayerTurns().add(newPLayerTurn);
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

    private PlayerTurn createNewPlayerTurn(Turn turn, PlayerTurn playerTurn, Hand newHand) {
        PlayerTurn newPLayerTurn = new PlayerTurn(turn.getId(), playerTurn.getPlayer());
        newPLayerTurn.setBet(playerTurn.getBet());
        newPLayerTurn.setHand(newHand);
        return newPLayerTurn;
    }
}
