package aDarbellay.s05.t1.exception;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException(Class<?> clazz, String id) {
        super(String.format("Error: entity of type %s with id %s not found.", clazz.getSimpleName(), id));
    }
}
