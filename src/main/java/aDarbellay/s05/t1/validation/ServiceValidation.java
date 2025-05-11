package aDarbellay.s05.t1.validation;

import aDarbellay.s05.t1.exception.NumPlayerOutOfBoundException;
import aDarbellay.s05.t1.model.games.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ServiceValidation {

    @Value("${blackjack.dev.numPlayerMax}")
    private int numPlayerMax;


    private Validator createValidator(String target) {
        return switch (target.toLowerCase().trim()) {
            case "numplayer" -> this::validateNumPlayer;
            default -> throw new IllegalStateException("Unexpected value: " + target.toLowerCase().trim());
        };
    }

    public Mono<Game> validateNumPlayer(Game game) {
        ValidationContext vc = new ValidationContext.VCBuilder().setGame(game).build();
        try {
            validateNumPlayer(vc);
            return Mono.just(game);
        } catch (NumPlayerOutOfBoundException ex) {
            return Mono.error(ex);
        }

    }

    private void validateNumPlayer(ValidationContext vc) {
        Game game = vc.getGame();
        if (game.getPlayers().size() > numPlayerMax)
            throw new NumPlayerOutOfBoundException(numPlayerMax);
    }

    public int getNumPlayerMax() {
        return numPlayerMax;
    }
}
