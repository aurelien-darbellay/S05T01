package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.hands.Hand;

import java.util.List;

public class PlayerTurn {

    private int turn;
    private Player player;
    private int bet;
    private List<Action> actions;
    private Hand hand;
    private boolean issuedFromSplit;
    private boolean issuedFromDoubleSplit;
    private boolean issuedFromTripleSplit;
    private boolean result;

    public PlayerTurn(int turn, Player player) {
        this.turn = turn;
        this.player = player;
        this.issuedFromSplit = false;
        this.issuedFromDoubleSplit = false;
        this.issuedFromTripleSplit = false;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(List<Card> cards, Hand hand) {
        hand.addAll(cards);
        this.hand = hand;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isIssuedFromSplit() {
        return issuedFromSplit;
    }

    public void setIssuedFromSplit(boolean issuedFromSplit) {
        this.issuedFromSplit = issuedFromSplit;
    }

    public boolean isIssuedFromDoubleSplit() {
        return issuedFromDoubleSplit;
    }

    public void setIssuedFromDoubleSplit(boolean issuedFromDoubleSplit) {
        this.issuedFromDoubleSplit = issuedFromDoubleSplit;
        if (issuedFromDoubleSplit) this.setIssuedFromSplit(true);
    }

    public boolean isIssuedFromTripleSplit() {
        return issuedFromTripleSplit;
    }

    public void setIssuedFromTripleSplit(boolean issuedFromTripleSplit) {
        this.issuedFromTripleSplit = issuedFromTripleSplit;
        if (issuedFromTripleSplit) this.setIssuedFromDoubleSplit(true);
    }
}
