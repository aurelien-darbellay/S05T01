package aDarbellay.s05.t1.exception;

public class IllegalBetException extends Exception {
    public IllegalBetException(String bigSmall) {
        super("Error, your bet is too " + bigSmall);
    }
}
