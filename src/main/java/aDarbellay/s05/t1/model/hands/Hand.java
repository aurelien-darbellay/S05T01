package aDarbellay.s05.t1.model.hands;

import aDarbellay.s05.t1.model.Card;

import java.util.ArrayList;

public class Hand extends ArrayList<Card> {

    public enum Visibility {
        PARTIAL, COMPLETE
    }

    public enum HandType {
        OWN, DEALER
    }

    public static Hand createHand(Visibility visibility, HandType handType) {
        Hand newHand = new Hand();
        newHand.setVisibility(visibility);
        newHand.setHandType(handType);
        return newHand;
    }

    private Visibility visibility;
    private HandType handType;


    public int getHandValue() {
        int value = this.stream().map(Card::getPoints).reduce(0, (a, b) -> a + b);
        if (value > 21 && hasAs()) return value - 10;
        else return value;
    }

    private boolean hasAs() {
        return this.stream().anyMatch(card -> card.getValue().contains("A"));
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public HandType getHandType() {
        return handType;
    }

    public void setHandType(HandType handType) {
        this.handType = handType;
    }
}
