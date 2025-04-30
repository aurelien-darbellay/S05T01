package aDarbellay.s05.t1.model.hands;

import aDarbellay.s05.t1.model.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandTest {
    static HandFactory handFactory;
    static List<Card> noAs;
    static List<Card> withAsGood;
    static List<Card> withAsBad;

    @BeforeAll
    static void setUp() {
        handFactory = new HandFactory();
        noAs = List.of(new Card("S6"), new Card("HQ"), new Card("D7"));
        withAsGood = List.of(new Card("SA"), new Card("H3"), new Card("D2"));
        withAsBad = List.of(new Card("SA"), new Card("HQ"), new Card("D7"));
    }

    @Test
    void getHandValue() {
        Hand testHand1 = handFactory.createHand(HandFactory.HandType.OWN);
        testHand1.addAll(noAs);
        assertEquals(23, testHand1.getHandValue());
        Hand testHadn2 = handFactory.createHand(HandFactory.HandType.DEALER);
        testHadn2.addAll(withAsGood);
        assertEquals(16, testHadn2.getHandValue());
        Hand testHand3 = handFactory.createHand(HandFactory.HandType.OTHER);
        testHand3.addAll(withAsBad);
        assertEquals(18, testHand3.getHandValue());
    }
}