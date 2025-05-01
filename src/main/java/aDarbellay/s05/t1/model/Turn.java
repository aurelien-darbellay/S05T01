package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private int id;
    private List<PlayerTurn> playerTurns;
    private Hand dealerHand;
    private ArrayList<Card> reserve;
    private TurnState turnState;

    public enum TurnState {
        STATE1("Bet placed; cards distributed"),
        STATE2("Players have chosen action - moving on to revealing the dealer's hand"),
        STATE3("Turn finished"),
        ONHOLD("Input from interactive player required to move forward.");

        private final String value;

        TurnState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Turn(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PlayerTurn> getPlayerTurns() {
        return playerTurns;
    }

    public void setPlayerTurns(List<PlayerTurn> playerTurns) {
        this.playerTurns = playerTurns;
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

    @Override
    public String toString() {
        return "Turn{" +
                "id=" + id +
                ", playerTurns=" + playerTurns +
                ", dealerHand=" + dealerHand +
                ", turnState =" + turnState.getValue() +
                '}';
    }
}
