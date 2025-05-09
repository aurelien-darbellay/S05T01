package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;

public class TurnBuilder {
    private int id;
    private List<PlayerStrategy> playerStrategies = new ArrayList<>();
    private Hand dealerHand;
    private ArrayList<Card> reserve = new ArrayList<>();
    private Turn.TurnState turnState = Turn.TurnState.STARTED;
    private boolean isInputRequired;
    private String gameId;

    public TurnBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TurnBuilder setPlayerStrategies(List<PlayerStrategy> playerStrategies) {
        this.playerStrategies = playerStrategies;
        return this;
    }

    public TurnBuilder setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
        return this;
    }

    public TurnBuilder setReserve(ArrayList<Card> reserve) {
        this.reserve = reserve;
        return this;
    }

    public TurnBuilder setTurnState(Turn.TurnState turnState) {
        this.turnState = turnState;
        return this;
    }

    public TurnBuilder setInputRequired(boolean inputRequired) {
        isInputRequired = inputRequired;
        return this;
    }

    public TurnBuilder setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public Turn build() {
        Turn newTurn = new Turn(this.id);
        newTurn.setTurnState(this.turnState);
        newTurn.setDealerHand(this.dealerHand);
        newTurn.setInputRequired(this.isInputRequired);
        newTurn.setGameId(this.gameId);
        newTurn.setPlayerStrategies(this.playerStrategies);
        newTurn.setReserve(this.reserve);
        return newTurn;
    }
}
