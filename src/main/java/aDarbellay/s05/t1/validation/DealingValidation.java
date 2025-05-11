package aDarbellay.s05.t1.validation;

import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.exception.IllegalBetException;
import aDarbellay.s05.t1.model.Bet;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.validation.ValidationContext.VCBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DealingValidation {

    @Value("${blackjack.dev.betMin}")
    private int minBet;
    @Value("${blackjack.dev.betMax}")
    private int maxBet;
    @Value("${blackjack.dev.noDoubleSplitWithAs}")
    private boolean noDoubleSplitWithAs;

    private Validator createValidator(String target) {
        return switch (target.toLowerCase().trim()) {
            case "split" -> this::validateSplit;
            case "bet" -> this::validateBet;
            default -> this::doesNotValidate;
        };
    }

    public void validateAction(Action playerAction, PlayerStrategy playerStrategy, Turn turn) throws IllegalActionException, IllegalBetException {
        ValidationContext vc = new VCBuilder().setAction(playerAction).setStrategy(playerStrategy).setTurn(turn).build();
        createValidator(playerAction.getClass().getSimpleName()).validate(vc);
    }

    private void validateSplit(ValidationContext validationContext) throws IllegalActionException {
        if (!handIsPair(validationContext.getStrategy().getHand()))
            throw new IllegalActionException("You can't split with your hand -- it isn't a pair");
        else if (noDoubleSplitWithAs && playerHasAlreadySplitWithAs(validationContext.getTurn(), validationContext.getStrategy()))
            throw new IllegalActionException("You've already split as many times ");
    }

    private boolean handIsPair(Hand hand) {
        return (hand.size() == 2 && hand.getFirst().getValue().endsWith(hand.getLast().getValue().substring(1)));
    }

    private boolean playerHasAlreadySplitWithAs(Turn turn, PlayerStrategy playerStrategy) {
        Player player = playerStrategy.getPlayer();
        return (2 <= turn.getPlayerStrategies().stream().filter(strategy -> strategy.getPlayer().equals(player)).count()
                && playerStrategy.getHand().stream().anyMatch(card -> card.getValue().contains("A")));
    }

    public void validateBet(Bet bet) throws IllegalActionException, IllegalBetException {
        ValidationContext vc = new VCBuilder().setBet(bet).build();
        createValidator(bet.getClass().getSimpleName()).validate(vc);
    }

    private void validateBet(ValidationContext validationContext) throws IllegalBetException {
        if (validationContext.getBet().getValue() < minBet) throw new IllegalBetException("small");
        else if (validationContext.getBet().getValue() > maxBet) throw new IllegalBetException("big");
    }

    private void doesNotValidate(ValidationContext vc) {
    }

    public int getMinBet() {
        return minBet;
    }

    public int getMaxBet() {
        return maxBet;
    }
}
