package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.hands.Hand;

import java.util.ArrayList;
import java.util.List;

public class PlayerStrategy {

    public enum ResultType {
        WIN, LOSS, PUSH
    }

    private int id;
    private int turn;
    private Player player;
    private Integer bet;
    private List<Action> actions = new ArrayList<>();
    private Hand hand;
    private ResultType result;

    public PlayerStrategy(int turn, Player player) {
        this.turn = turn;
        this.player = player;
        this.id = player.getId();
    }

    @Override
    public String toString() {
        return "PlayerStrategy{" +
                "id="+ id +
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

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
