package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.hands.HandType;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PlayerTurn {

    private int turn;
    private Player player;
    private int bet;
    private List<Action> actions;
    private Hand hand;
    private boolean result;

    public PlayerTurn(int turn, Player player) {
        this.turn = turn;
        this.player = player;
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

    public void setHand(List<Card> cards, HandType handType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends Hand> classHand = handType.getClassHand();
        Hand newHand = classHand.getDeclaredConstructor().newInstance();;
        newHand.addAll(cards);
        this.hand = newHand;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
