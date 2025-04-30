package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.actions.Action;

public interface Player {

    void placeBet(PlayerTurn playerTurn);

    Action pickAction();
}
