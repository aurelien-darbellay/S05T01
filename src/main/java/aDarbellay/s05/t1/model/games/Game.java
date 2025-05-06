package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.player.Player;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        /*StringBuilder turnsSummary = new StringBuilder();
        turnsPlayed.forEach(turn -> {
            turnsSummary.append("\n Turn ").append(turn.getId()).append(":\n");
            turnsSummary.append("Dealer Hand :").append(turn.getDealerHand().toString()).append("\n");
            turn.getPlayerTurns().forEach(playerTurn -> {
                turnsSummary.append("\n").append(playerTurn.toString());
            });
        });*/
        return "Game{ id = " + id + " }";
    }

    public boolean isGameOn() {
        return (getActiveTurn() != null);
    }
}
