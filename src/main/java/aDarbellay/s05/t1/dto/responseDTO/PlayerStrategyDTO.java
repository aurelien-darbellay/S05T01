package aDarbellay.s05.t1.dto.responseDTO;

import java.util.List;
import java.util.Objects;

public class PlayerStrategyDTO {
    private final PlayerDTO player;
    private final Integer bet;
    private final List<String> actions;
    private final List<String> cards;
    private final String result;

    public PlayerStrategyDTO(PlayerDTO player, Integer bet, List<String> actions, List<String> cards, String result) {
        this.player = player;
        this.bet = bet;
        this.actions = actions;
        this.cards = cards;
        this.result = result;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public Integer getBet() {
        return bet;
    }

    public List<String> getActions() {
        return actions;
    }

    public List<String> getCards() {
        return cards;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStrategyDTO that = (PlayerStrategyDTO) o;
        return Objects.equals(player, that.player) && Objects.equals(bet, that.bet) && Objects.equals(actions, that.actions) && Objects.equals(cards, that.cards) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, bet, actions, cards, result);
    }
}
