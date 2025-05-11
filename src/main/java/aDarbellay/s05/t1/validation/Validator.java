package aDarbellay.s05.t1.validation;

import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.exception.IllegalBetException;

public interface Validator {
    void validate(ValidationContext validationContext) throws IllegalBetException, IllegalActionException;
}
