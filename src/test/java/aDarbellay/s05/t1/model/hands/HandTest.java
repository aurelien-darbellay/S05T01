package aDarbellay.s05.t1.model.hands;

import aDarbellay.s05.t1.model.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandTest {

    static List<Card> noAs;
    static List<Card> withAsGood;
    static List<Card> withAsBad;

    @BeforeAll
    static void setUp() {

        noAs = List.of(new Card("S6"), new Card("HQ"), new Card("D7"));
        withAsGood = List.of(new Card("SA"), new Card("H3"), new Card("D2"));
        withAsBad = List.of(new Card("SA"), new Card("HQ"), new Card("D7"));
    }

    @Test
    void getHandValue() {
        Hand testHand1 = Hand.createPlayerHand(noAs);
        assertEquals(23, testHand1.getHandValue());
        Hand testHadn2 = Hand.createDealerHand(withAsGood);
        assertEquals(16, testHadn2.getHandValue());
        Hand testHand3 = Hand.createPlayerHand(withAsBad);
        assertEquals(18, testHand3.getHandValue());
    }
}