package aDarbellay.s05.t1.model;

public class Card {
    private String value;

    public Card(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPoints() {
        if (value.endsWith("A")) return 11;
        if (value.endsWith("J") || value.endsWith("Q") || value.endsWith("K")) return 10;
        try {
            return Integer.parseInt(value.substring(1));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
