package aDarbellay.s05.t1.model.player;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.games.PlayerStrategy;


public class RealPlayer implements Player{

    private long id;
    private String firstname;
    private String lastname;
    private long points = 0;



    @Override
    public void placeBet(PlayerStrategy playerStrategy, Integer quantity) {

    }

    @Override
    public Action pickAction(ActionType actionType) {
        return null;
    }

    @Override
    public boolean isInteractive() {
        return true;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
