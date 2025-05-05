package aDarbellay.s05.t1.model;

import javax.print.attribute.IntegerSyntax;

public class Bet {
    private int value;
    private boolean used;

    public Bet(int value) {
        this.value = value;
        this.used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }



}
