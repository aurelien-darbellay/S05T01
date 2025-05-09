package aDarbellay.s05.t1.model.player;

import aDarbellay.s05.t1.model.actions.*;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("player")
public class RealPlayer implements Player {

    @Id
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void addPoints(long x) {
        this.points += x;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
