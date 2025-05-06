package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private int id;
    private List<PlayerStrategy> playerStrategies;
    private Hand dealerHand;
    private ArrayList<Card> reserve;
    private TurnState turnState;
    private boolean isInputRequired;
    private String gameId;

    public enum TurnState {
        STARTED("Turn just started"),
        BETS_PLACED("Bet placed"),
        HANDS_DISTRIBUTED("Cards distributed"),
        PLAYERS_CHOOSE_STRATEGY("The players are choosing their strategies"),
        HANDS_PLAYED("Players have chosen action - moving on to revealing the dealer's hand"),
        TURN_FINISHED("Turn finished");

        private final String value;

        TurnState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Turn(int id) {
        this.turnState = TurnState.STARTED;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PlayerStrategy> getPlayerStrategies() {
        return playerStrategies;
    }

    public void setPlayerStrategies(List<PlayerStrategy> playerStrategies) {
        this.playerStrategies = new ArrayList<>(playerStrategies);
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public List<Card> getReserve() {
        return reserve;
    }

    public void setReserve(List<Card> reserve) {
        this.reserve = new ArrayList<>(reserve);
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isInputRequired() {
        return isInputRequired;
    }

    public void setInputRequired(boolean inputRequired) {
        isInputRequired = inputRequired;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "id=" + id +
                ", playerStrategies=" + playerStrategies +
                ", dealerHand=" + dealerHand +
                ", turnState =" + turnState.getValue() +
                '}';
    }
}
