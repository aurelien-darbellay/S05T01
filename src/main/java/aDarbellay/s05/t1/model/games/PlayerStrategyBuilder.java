package aDarbellay.s05.t1.model.games;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerStrategyBuilder {

    private int id;
    private int turn;
    private Player player;
    private Integer bet;
    private List<Action> actions = new ArrayList<>();
    private Hand hand;
    private PlayerStrategy.ResultType result;

    public PlayerStrategyBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PlayerStrategyBuilder setTurn(int turn) {
        this.turn = turn;
        return this;
    }

    public PlayerStrategyBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public PlayerStrategyBuilder setBet(Integer bet) {
        this.bet = bet;
        return this;
    }

    public PlayerStrategyBuilder setActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public PlayerStrategyBuilder setHand(Hand hand) {
        this.hand = hand;
        return this;
    }

    public PlayerStrategyBuilder setResult(PlayerStrategy.ResultType result) {
        this.result = result;
        return this;
    }

    public PlayerStrategy build() {
        PlayerStrategy strategy = new PlayerStrategy(this.turn, this.player);
        strategy.setActions(this.actions);
        strategy.setId(this.id);
        strategy.setHand(this.hand);
        strategy.setBet(this.bet);
        strategy.setResult(this.result);
        return strategy;
    }
}
