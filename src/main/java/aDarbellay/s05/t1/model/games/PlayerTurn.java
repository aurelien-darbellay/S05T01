package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.Player;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;

public class PlayerTurn {

    public enum ResultType {
        WIN, LOSS, PUSH
    }

    private int turn;
    private Player player;
    private int bet;
    private List<Action> actions;
    private Hand hand;
    private ResultType result;

    public PlayerTurn(int turn, Player player) {
        this.turn = turn;
        this.player = player;
        this.actions = new ArrayList<Action>();
    }

    @Override
    public String toString() {
        return "PlayerTurn{" +
                "turn=" + turn +
                ", player=" + player +
                ", bet=" + bet +
                ", actions=" + actions +
                ", hand=" + hand +
                ", result=" + result +
                '}';
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

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public ResultType getResult() {
        return result;
    }

    public void setResult(ResultType result) {
        this.result = result;
    }

}
