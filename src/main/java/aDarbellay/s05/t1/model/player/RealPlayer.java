package aDarbellay.s05.t1.model.player;

import aDarbellay.s05.t1.model.actions.*;
import aDarbellay.s05.t1.model.games.PlayerStrategy;


public class RealPlayer implements Player{

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private long points = 0;



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
        return this.id;
    }

    @Override
    public void setId(int id) {
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

    public void addPoints(long x) {this.points += x;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
