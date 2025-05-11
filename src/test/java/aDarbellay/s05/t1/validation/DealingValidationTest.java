package aDarbellay.s05.t1.validation;

import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.model.Bet;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.actions.Hit;
import aDarbellay.s05.t1.model.actions.Split;
import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.PlayerStrategyBuilder;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.CautiousPlayer;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")  // Uses src/test/resources/application-test.yml
class DealingValidationTest {

    @Autowired
    private DealingValidation dealingValidation;

    @Autowired
    private PlayerFactory playerFactory;

    @Test
    void testInjectedValue() {
        assertEquals(20, dealingValidation.getMinBet());
    }

    @Test
    void testValidateSplit() {
        Hand testHand = new Hand();
        testHand.addAll(List.of(new Card("HA"), new Card("S10")));
        PlayerStrategy strategy = new PlayerStrategy(1, new CautiousPlayer());
        strategy.setHand(testHand);
        Action action = new Split();
        assertThrows(IllegalActionException.class, () -> dealingValidation.validateAction(action, strategy, new Turn(1)));

        Player maria = playerFactory.createNewPlayer("María", "Ferrer", "MaFerrer");
        Turn newTurn = new Turn(2);
        Hand handMaria1 = new Hand();
        handMaria1.addAll(List.of(new Card("HA"), new Card("S10")));
        Hand handMaria2 = new Hand();
        handMaria2.addAll(List.of(new Card("SA"), new Card("CA")));
        PlayerStrategy strategy1 = new PlayerStrategyBuilder().setPlayer(maria).setHand(handMaria1).setTurn(2).build();
        PlayerStrategy strategy2 = new PlayerStrategyBuilder().setPlayer(maria).setHand(handMaria2).setTurn(2).build();
        newTurn.setPlayerStrategies(List.of(strategy1, strategy2));
        assertThrows(IllegalActionException.class, () -> dealingValidation.validateAction(action, strategy2, newTurn));
        assertThatNoException()
                .isThrownBy(() -> dealingValidation.validateAction(action, strategy2, new Turn(1)));
        assertThatNoException()
                .isThrownBy(() -> dealingValidation.validateAction(new Hit(), strategy2, newTurn));
    }

    @Test
    void testValidateBet() {
        assertThatException()
                .isThrownBy(() -> dealingValidation.validateBet(new Bet(3)))
                .withMessage("Error, your bet is too small");
        assertThatException()
                .isThrownBy(() -> dealingValidation.validateBet(new Bet(10000)))
                .withMessage("Error, your bet is too big");
    }
}