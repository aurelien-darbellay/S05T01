package aDarbellay.s05.t1.validation;

import aDarbellay.s05.t1.model.Bet;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;

public class ValidationContext {
    private final Game game;
    private final Turn turn;
    private final Bet bet;
    private final PlayerStrategy strategy;
    private final Action action;

    public ValidationContext(VCBuilder builder) {
        this.game = builder.game;
        this.turn = builder.turn;
        this.bet = builder.bet;
        this.strategy = builder.strategy;
        this.action = builder.action;
    }

    public static class VCBuilder {
        private Game game;
        private Turn turn;
        private Bet bet;
        private PlayerStrategy strategy;
        private Action action;

        public VCBuilder setGame(Game game) {
            this.game = game;
            return this;
        }

        public VCBuilder setTurn(Turn turn) {
            this.turn = turn;
            return this;
        }

        public VCBuilder setBet(Bet bet) {
            this.bet = bet;
            return this;
        }

        public VCBuilder setStrategy(PlayerStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public VCBuilder setAction(Action action) {
            this.action = action;
            return this;
        }

        public ValidationContext build() {
            return new ValidationContext(this);
        }
    }

    public Bet getBet() {
        return bet;
    }

    public Turn getTurn() {
        return turn;
    }

    public Game getGame() {
        return game;
    }

    public Action getAction() {
        return action;
    }

    public PlayerStrategy getStrategy() {
        return strategy;
    }
}
