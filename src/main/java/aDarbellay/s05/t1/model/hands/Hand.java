package aDarbellay.s05.t1.model.hands;

import aDarbellay.s05.t1.model.Card;

import java.util.ArrayList;

public abstract class Hand extends ArrayList<Card> {
    public enum Visibility {
        PARTIAL, COMPLETE, HIDDEN
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    private Visibility visibility;

    public int getHandValue() {
        int value = this.stream().map(Card::getPoints).reduce(0, (a, b) -> a + b);
        if (value > 21 && hasAs()) return value - 10;
        else return value;
    }

    private boolean hasAs() {
        return this.stream().anyMatch(card -> card.getValue().contains("A"));
    }
}
