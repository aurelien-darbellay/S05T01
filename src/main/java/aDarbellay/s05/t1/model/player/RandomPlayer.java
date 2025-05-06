package aDarbellay.s05.t1.model.player;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.actions.Hit;
import aDarbellay.s05.t1.model.actions.Stand;
import aDarbellay.s05.t1.model.games.PlayerStrategy;

public class RandomPlayer implements Player {

    @Override
    public void placeBet(PlayerStrategy playerStrategy, Integer bet) {
        playerStrategy.setBet((int) Math.floor(Math.random() * 50));
    }

    @Override
    public Action pickAction(ActionType actionType) {
        return Math.random() < 0.5 ? new Stand() : new Hit();
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int i) {

    }

    @Override
    public String toString() {
        return "RandomPlayer{}";
    }
}