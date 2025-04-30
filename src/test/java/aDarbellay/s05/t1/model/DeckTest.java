package aDarbellay.s05.t1.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckCreation(){
        Deck newDeck = new Deck();
        newDeck.forEach(System.out::println);
    }
}