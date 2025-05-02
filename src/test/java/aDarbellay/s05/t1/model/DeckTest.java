package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.cards.Deck;
import org.junit.jupiter.api.Test;


class DeckTest {

    @Test
    void testDeckCreation() {
        Deck newDeck = new Deck();
        newDeck.forEach(card -> System.out.println(card.getValue()));
    }
}