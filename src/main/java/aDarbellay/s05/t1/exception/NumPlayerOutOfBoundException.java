package aDarbellay.s05.t1.exception;

public class NumPlayerOutOfBoundException extends RuntimeException {
    public NumPlayerOutOfBoundException(int num) {
        super("Error: you cannot create a game with more than " + num + " players.");
    }
}
