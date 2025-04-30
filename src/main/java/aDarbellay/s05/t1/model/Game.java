package aDarbellay.s05.t1.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Game {
    private List<Player> players;
    private int numTurnsPlayed;

    public int getNumTurnsPlayed() {
        return numTurnsPlayed;
    }
    public void setNumTurnsPlayed(int numTurnsPlayed) {
        this.numTurnsPlayed = numTurnsPlayed;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
