package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testClasses.CautiousPlayer;
import testClasses.RandomPlayer;

import java.util.List;

@DataMongoTest
@Import({GameService.class, Dealer.class, Deck.class, DealingValidation.class, GameManager.class})
class GameServiceTest {
    @Autowired
    GameService gameService;

    @Test
    void saveGame() {
        Game newGame = new Game();
        newGame.setId("fromService");
        newGame.setPlayers(List.of(new RandomPlayer()));
        gameService.saveGame(newGame).block();
    }

    @Test
    void findGameById() {
        Mono<Game> result = gameService.findGameById("fromService");
        result.subscribe(
                game -> System.out.println(game.getId())
        );
    }

    @Test
    void updateGame() {
        Game newGame = new Game();
        newGame.setId("fromService");
        newGame.setPlayers(List.of(new CautiousPlayer()));
        newGame.setTurnsPlayed(List.of(new Turn(1)));
        Mono<Game> result = gameService.updateGame("fromService", newGame)
                .doOnNext(game -> System.out.println(game.toString()));
        StepVerifier.create(result).expectNextMatches(game -> game.getTurnsPlayed().size() == 1 && game.getPlayers().getFirst() instanceof CautiousPlayer).verifyComplete();

    }
}