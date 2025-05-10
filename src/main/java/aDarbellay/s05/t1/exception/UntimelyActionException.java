package aDarbellay.s05.t1.exception;

public class UntimelyActionException extends Exception {
    public UntimelyActionException(Class<?> clazz) {
        super(String.format("Error: it is not the time to try and do an %s.", clazz.getSimpleName()));
    }
}
