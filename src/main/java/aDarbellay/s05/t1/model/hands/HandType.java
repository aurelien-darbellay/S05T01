package aDarbellay.s05.t1.model.hands;

public enum HandType {

    OWN(Visibility.COMPLETE, OwnHand.class),DEALER(Visibility.PARTIAL, DealerHand.class),OTHER(Visibility.HIDDEN, OtherHand.class);

    public enum Visibility {
        PARTIAL,COMPLETE,HIDDEN
    }

    private Visibility visibility;
    private Class<? extends Hand> classHand;

    HandType(Visibility visibility,Class<? extends Hand> classHand){
        this.visibility = visibility;
        this.classHand = classHand;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
    public Class<? extends Hand> getClassHand() {
        return classHand;
    }

    public void setClassHand(Class<? extends Hand> classHand) {
        this.classHand = classHand;
    }


}
