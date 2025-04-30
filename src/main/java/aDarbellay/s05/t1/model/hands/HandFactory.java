package aDarbellay.s05.t1.model.hands;

public class HandFactory {

    public enum HandType {
        OWN, OTHER, DEALER
    }

    public Hand createHand(HandType type) {
        return switch (type) {
            case OWN -> new OwnHand();
            case OTHER -> new OtherHand();
            case DEALER -> new DealerHand();
        };
    }
}
