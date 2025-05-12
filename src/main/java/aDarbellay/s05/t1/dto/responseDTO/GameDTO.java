package aDarbellay.s05.t1.dto.responseDTO;

import java.util.List;
import java.util.Objects;

public class GameDTO {
    private String id;
    private final List<PlayerDTO> players;
    private final List<TurnDTO> turnsPlayed;
    private final TurnDTO activeTurn;
    private final boolean gameOn;

    public GameDTO(String id, List<PlayerDTO> players, List<TurnDTO> turnsPlayed, TurnDTO activeTurn, boolean gameOn) {
        this.id = id;
        this.players = players;
        this.turnsPlayed = turnsPlayed;
        this.activeTurn = activeTurn;
        this.gameOn = gameOn;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public List<TurnDTO> getTurnsPlayed() {
        return turnsPlayed;
    }

    public TurnDTO getActiveTurn() {
        return activeTurn;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO gameDTO = (GameDTO) o;
        return gameOn == gameDTO.gameOn && Objects.equals(players, gameDTO.players) && Objects.equals(turnsPlayed, gameDTO.turnsPlayed) && Objects.equals(activeTurn, gameDTO.activeTurn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players, turnsPlayed, activeTurn, gameOn);
    }
}
