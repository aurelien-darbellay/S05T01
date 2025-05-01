package aDarbellay.s05.t1.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Game {

    private String id;
    private List<Player> players;
    private List<Turn> turnsPlayed;
    private Turn activeTurn;

    public List<Turn> getTurnsPlayed() {
        return turnsPlayed;
    }

    public void setTurnsPlayed(List<Turn> turnsPlayed) {
        this.turnsPlayed = turnsPlayed;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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
        StringBuilder turnsSummary = new StringBuilder();
        turnsPlayed.forEach(turn -> {
            turnsSummary.append("\n Turn ").append(turn.getId()).append(":\n");
            turnsSummary.append("Dealer Hand :").append(turn.getDealerHand().toString()).append("\n");
            turn.getPlayerTurns().forEach(playerTurn -> {
                turnsSummary.append("\n").append(playerTurn.toString());
            });
        });
        return "Game{" +
                "players=" + players +
                ", turnsPlayed=" + turnsSummary +
                '}';
    }
}
