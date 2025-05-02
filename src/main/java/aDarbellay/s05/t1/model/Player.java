package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.games.PlayerTurn;

public interface Player {

    void placeBet(PlayerTurn playerTurn, int quantity);

    Action pickAction(ActionType actionType);

    boolean isInteractive();
}
