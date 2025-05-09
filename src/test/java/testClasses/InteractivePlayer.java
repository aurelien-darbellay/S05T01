package testClasses;

import aDarbellay.s05.t1.model.actions.*;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.player.Player;

public class InteractivePlayer implements Player {
    private int id;

    @Override
    public void placeBet(PlayerStrategy playerStrategy, Integer quantity) {
        playerStrategy.setBet(quantity);
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
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public long getPoints() {
        return 0;
    }

    @Override
    public String getFirstName() {
        return "Interactive";
    }

    @Override
    public String getLastName() {
        return "Player";
    }

    @Override
    public String getUserName() {
        return "interactivePlayer";
    }

    @Override
    public String toString() {
        return "InteractivePlayer{}";
    }
}
