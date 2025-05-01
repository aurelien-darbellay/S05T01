package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.model.*;
import aDarbellay.s05.t1.model.hands.Hand;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SplitTest {

    static class MockPlayer implements Player {
        @Override
        public void placeBet(PlayerTurn playerTurn, int bet) {
        }

        @Override
        public Action pickAction(ActionType actionType) {
            return null;
        }

        @Override
        public boolean isInteractive() {
            return false;
        }
    }

    static private List<Card> getNCardFromReserve(int n, List<Card> reserve) {
        List<Card> drawnCards = new ArrayList<>(reserve.subList(0, n));
        reserve.subList(0, n).clear(); // remove them from deck
        return drawnCards;
    }

    @Test
    void execute() {
        Turn newTurn = new Turn(1);
        List<Card> reserve = new Deck();
        newTurn.setReserve(reserve);
        Player mockPlayer = new MockPlayer();
        PlayerTurn playerTurn = new PlayerTurn(1, mockPlayer);
        newTurn.setPlayerTurns(new ArrayList<>(List.of(playerTurn)));
        Card card1 = reserve.get(1);
        reserve.remove(card1);
        Card card2 = reserve.get(14);
        reserve.remove(card2);
        List<Card> cards = List.of(card1, card2);
        playerTurn.setHand(Hand.createPlayerHand(cards));
        playerTurn.setBet(20);
        Deque<PlayerTurn> turnsToPlay = new ArrayDeque<>();
        Action split = new Split();
        split.execute(newTurn, turnsToPlay, playerTurn, SplitTest::getNCardFromReserve);
        assertEquals(1, playerTurn.getActions().size());
        assertInstanceOf(Split.class, playerTurn.getActions().getFirst());
        assertEquals(1, turnsToPlay.size());
        assertEquals(20, turnsToPlay.getFirst().getBet());
        assertEquals(2, turnsToPlay.getFirst().getHand().size());
        assertEquals(2, playerTurn.getHand().size());
    }
}