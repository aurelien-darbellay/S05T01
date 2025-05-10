package aDarbellay.s05.t1.model.actions;

import aDarbellay.s05.t1.exception.IllegalActionException;

public enum ActionType {
    HIT("Hit"), STAND("Stand"), DOUBLE("Double"), SPLIT("Split");
    public final String simpleName;

    ActionType(String simpleName) {
        this.simpleName = simpleName;
    }

    static public ActionType matchStringToActionType(String str) throws IllegalActionException {
        return switch (str.toLowerCase().trim()) {
            case "hit" -> ActionType.HIT;
            case "stand" -> ActionType.STAND;
            case "double" -> ActionType.DOUBLE;
            case "split" -> ActionType.SPLIT;
            default -> throw new IllegalActionException("Unrecognized Action Type");
        };
    }
}
