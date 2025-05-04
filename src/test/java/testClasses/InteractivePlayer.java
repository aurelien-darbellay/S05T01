package testClasses;

import aDarbellay.s05.t1.model.Player;
import aDarbellay.s05.t1.model.actions.*;
import aDarbellay.s05.t1.model.games.PlayerStrategy;

public class InteractivePlayer implements Player {

    @Override
    public void placeBet(PlayerStrategy playerStrategy, int quantity) {

    }

    @Override
    public Action pickAction(ActionType actionType) {
        return switch (actionType) {
            case HIT -> new Hit();
            case SPLIT -> new Split();
            case STAND -> new Stand();
            case DOUBLE -> new DoubleBet();
        };
    }

    @Override
    public boolean isInteractive() {
        return true;
    }

    @Override
    public String toString() {
        return "InteractivePlayer{}";
    }
}
