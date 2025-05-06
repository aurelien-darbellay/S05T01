package aDarbellay.s05.t1.model.player;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.games.PlayerStrategy;

public interface Player {

    void placeBet(PlayerStrategy playerStrategy, Integer quantity);

    Action pickAction(ActionType actionType);

    boolean isInteractive();

    default boolean isAutomatic() {
        return !isInteractive();
    }

    int getId();

    void setId(int i);
}
