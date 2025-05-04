package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.games.PlayerStrategy;

public interface Player {

    void placeBet(PlayerStrategy playerStrategy, int quantity);

    Action pickAction(ActionType actionType);

    boolean isInteractive();
}
