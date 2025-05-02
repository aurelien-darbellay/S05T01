package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.cards.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPoints() {
        Card testAs = new Card("HA");
        assertEquals(11, testAs.getPoints());
        assertEquals(2, new Card("S2").getPoints());
        assertEquals(10, new Card("DQ").getPoints());
    }
}