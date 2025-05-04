package testClasses;

import aDarbellay.s05.t1.model.Player;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.actions.Stand;
import aDarbellay.s05.t1.model.games.PlayerStrategy;

public class CautiousPlayer implements Player {
    @Override
    public String toString() {
        return "CautiousPLayer{}";
    }

    @Override
    public void placeBet(PlayerStrategy playerStrategy, int bet) {
        playerStrategy.setBet(2);
    }

    @Override
    public Action pickAction(ActionType actionType) {
        return new Stand();
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

}
