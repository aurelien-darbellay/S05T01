package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameBuilder {
    private String id;
    private List<Player> players = new ArrayList<>();
    private List<Turn> turnsPlayed = new ArrayList<>();
    private Turn activeTurn;
    private boolean gameOn;

    public GameBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public GameBuilder setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    public GameBuilder setTurnsPlayed(List<Turn> turnsPlayed) {
        this.turnsPlayed = turnsPlayed;
        return this;
    }

    public GameBuilder setActiveTurn(Turn activeTurn) {
        this.activeTurn = activeTurn;
        return this;
    }

    public GameBuilder setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
        return this;
    }

    public Game build() {
        Game newGame = new Game();
        newGame.setId(this.id);
        newGame.setPlayers(this.players);
        newGame.setTurnsPlayed(this.turnsPlayed);
        newGame.setActiveTurn(this.activeTurn);
        newGame.setGameOn(this.gameOn);
        return newGame;
    }
}
