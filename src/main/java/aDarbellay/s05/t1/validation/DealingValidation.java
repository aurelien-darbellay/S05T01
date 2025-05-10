package aDarbellay.s05.t1.validation;

import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import org.springframework.stereotype.Component;

@Component
public class DealingValidation {

    private class ActionValidatorFactory {
        static Validator createValidator(Class<?> clazz) throws IllegalActionException {
            return switch (ActionType.matchStringToActionType(clazz.getSimpleName())) {
                case ActionType.SPLIT -> DealingValidation::validateSplit;
                default -> throw new IllegalActionException("Action " + clazz.getSimpleName() + "not recognized");
            };
        }
    }

    public void validateAction(Action playerAction, PlayerStrategy playerStrategy) throws IllegalActionException {
        if (!ActionValidatorFactory.createValidator(playerAction.getClass()).validate(playerAction, playerStrategy))
            throw new IllegalActionException("Action " + playerAction.getClass().getSimpleName() + " not allowed in this context");
    }

    static private boolean validateSplit(Action action, PlayerStrategy strategy) {
        return true;
    }
}
