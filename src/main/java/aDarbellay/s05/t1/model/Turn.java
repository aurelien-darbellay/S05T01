package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private int id;
    private List<PlayerTurn> playerTurns;
    private List<PlayerTurn> splitTurns;
    private List<PlayerTurn> doubleSplitTurns;
    private List<PlayerTurn> tripleSplitTurns;
    private Hand dealerHand;
    private ArrayList<Card> reserve;

    public Turn(int id) {
        this.id = id;
        this.splitTurns = new ArrayList<PlayerTurn>();
        this.doubleSplitTurns = new ArrayList<PlayerTurn>();
        this.tripleSplitTurns = new ArrayList<PlayerTurn>();
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

    public void setDealerHand(List<Card> cards, Hand dealerHand) {
        dealerHand.addAll(cards);
        this.dealerHand = dealerHand;
    }

    public List<Card> getReserve() {
        return reserve;
    }

    public void setReserve(List<Card> reserve) {
        this.reserve = new ArrayList<>(reserve);
    }

    public List<PlayerTurn> getSplitTurns() {
        return splitTurns;
    }

    public void setSplitTurns(List<PlayerTurn> splitTurns) {
        this.splitTurns = splitTurns;
    }

    public List<PlayerTurn> getDoubleSplitTurns() {
        return doubleSplitTurns;
    }

    public void setDoubleSplitTurns(List<PlayerTurn> doubleSplitTurns) {
        this.doubleSplitTurns = doubleSplitTurns;
    }

    public List<PlayerTurn> getTripleSplitTurns() {
        return tripleSplitTurns;
    }

    public void setTripleSplitTurns(List<PlayerTurn> tripleSplitTurns) {
        this.tripleSplitTurns = tripleSplitTurns;
    }
}
