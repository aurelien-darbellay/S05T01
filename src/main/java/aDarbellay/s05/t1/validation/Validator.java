package aDarbellay.s05.t1.validation;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.games.PlayerStrategy;

public interface Validator {
    boolean validate(Action action, PlayerStrategy strategy);
}
