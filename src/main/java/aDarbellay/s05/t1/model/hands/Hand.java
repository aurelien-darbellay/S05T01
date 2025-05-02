package aDarbellay.s05.t1.model.hands;

import aDarbellay.s05.t1.model.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand extends ArrayList<Card> {

    public enum Visibility {
        PARTIAL, COMPLETE
    }

    public enum HandType {
        PLAYER, DEALER
    }

    public static Hand createPlayerHand(List<Card> cards) {
        Hand newHand = new Hand();
        newHand.setVisibility(Visibility.COMPLETE);
        newHand.setHandType(HandType.PLAYER);
        newHand.addAll(cards);
        return newHand;
    }

    public static Hand createDealerHand(List<Card> cards) {
        Hand newHand = new Hand();
        newHand.setVisibility(Visibility.PARTIAL);
        newHand.setHandType(HandType.DEALER);
        newHand.addAll(cards);
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

    @Override
    public String toString() {
        StringBuilder handSummary = new StringBuilder();
        this.forEach(card -> {
            handSummary.append(card.toString()).append(" ");
        });
        return "Hand{" +
                "visibility=" + visibility +
                ", handType=" + handType +
                ",cards=" + handSummary +
                '}';
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
