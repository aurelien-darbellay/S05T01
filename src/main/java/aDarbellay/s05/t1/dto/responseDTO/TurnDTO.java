package aDarbellay.s05.t1.dto.responseDTO;


import java.util.List;
import java.util.Objects;

public class TurnDTO {
    private final int id;
    private final List<PlayerStrategyDTO> playerStrategies;
    private final List<String> dealerHand;
    private final String turnState;
    private final boolean isInputRequired;

    public TurnDTO(int id, List<PlayerStrategyDTO> playerStrategies, List<String> dealerHand, String turnState, boolean isInputRequired) {
        this.id = id;
        this.playerStrategies = playerStrategies;
        this.dealerHand = dealerHand;
        this.turnState = turnState;
        this.isInputRequired = isInputRequired;
    }

    public int getId() {
        return id;
    }

    public List<PlayerStrategyDTO> getPlayerStrategies() {
        return playerStrategies;
    }

    public List<String> getDealerHand() {
        return dealerHand;
    }

    public String getTurnState() {
        return turnState;
    }

    public boolean isInputRequired() {
        return isInputRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnDTO turnDTO = (TurnDTO) o;
        return id == turnDTO.id && isInputRequired == turnDTO.isInputRequired && Objects.equals(playerStrategies, turnDTO.playerStrategies) && Objects.equals(dealerHand, turnDTO.dealerHand) && Objects.equals(turnState, turnDTO.turnState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerStrategies, dealerHand, turnState, isInputRequired);
    }
}
