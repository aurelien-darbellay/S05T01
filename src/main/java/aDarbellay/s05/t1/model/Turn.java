package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private int id;
    private List<PlayerTurn> playerTurns;
    private Hand dealerHand;
    private ArrayList<Card> reserve;

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


}
