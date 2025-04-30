package aDarbellay.s05.t1.model.hands;

import aDarbellay.s05.t1.model.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand extends ArrayList<Card> {
    public enum Visibility {
        PARTIAL,COMPLETE,HIDDEN
    }
    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
    private Visibility visibility;
}
