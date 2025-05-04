package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.validation.DealingValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
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
        //What happens if I try to
        Game newGame = new Game();
        newGame.setId("toDelete");
        newGame.setPlayers(List.of(new RandomPlayer()));
        gameService.saveGame(newGame).block();
    }

    @Test
    void findGameById() {
        Mono<Game> result = gameService.getGameById("fromService");
        result.subscribe(
                game -> System.out.println(game.getId())
        );
    }

    @Test
    void saveUpdatedGame() {
        Game newGame = new Game();
        newGame.setId("fromService");
        newGame.setPlayers(List.of(new CautiousPlayer(), new RandomPlayer()));
        newGame.setTurnsPlayed(List.of(new Turn(1), new Turn(2)));
        Mono<Game> result = gameService.saveUpdatedGame("fromService", newGame)
                .doOnNext(game -> System.out.println(game.toString()));
        StepVerifier.create(result).expectNextMatches(game -> game.getTurnsPlayed().size() == 2 && game.getPlayers().getFirst() instanceof CautiousPlayer).verifyComplete();
    }

    @Test
    void deleteById() {
        Mono<Void> deleteResult = gameService.deleteById("absent")
                .doAfterTerminate(() -> System.out.println("completed"));
        StepVerifier.create(deleteResult).verifyError(EntityNotFoundException.class);
    }

    @Test
    void deleteGame() {
        Mono<Void> deleteResult = gameService.getGameById("toDelete")
                .flatMap(gameService::deleteGame);
        StepVerifier.create(deleteResult).verifyComplete();
    }

    @Test
    void findAll() {
        /*List<String> ids = List.of("1", "2", "3", "4", "5");
        ids.stream()
                .map(id -> {
                    Game newGame = new Game();
                    newGame.setId(id);
                    return newGame;
                }).forEach(game -> gameService.saveGame(game).block());*/
        Flux<Game> results = gameService.getAllGame();
        StepVerifier.create(results).thenConsumeWhile(game -> {
            System.out.println(game.getId());
            return true;
        }).verifyComplete();
    }


}