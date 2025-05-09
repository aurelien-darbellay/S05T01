package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.player.Player;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Game {

    @Id
    private String id;
    private List<Player> players = new ArrayList<>();
    private List<Turn> turnsPlayed = new ArrayList<>();
    private Turn activeTurn;
    private boolean gameOn;

    public List<Turn> getTurnsPlayed() {
        return turnsPlayed;
    }

    public void setTurnsPlayed(List<Turn> turnsPlayed) {
        this.turnsPlayed = new ArrayList<>(turnsPlayed);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Turn getActiveTurn() {
        return activeTurn;
    }

    public void setActiveTurn(Turn activeTurn) {
        this.activeTurn = activeTurn;
    }

    public boolean isGameOn() {
        return (getActiveTurn() != null);
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    @Override
    public String toString() {
        return "Game{ id = " + id + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameOn == game.gameOn && Objects.equals(id, game.id) && Objects.equals(players, game.players) && Objects.equals(turnsPlayed, game.turnsPlayed) && Objects.equals(activeTurn, game.activeTurn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, players, turnsPlayed, activeTurn, gameOn);
    }

}
