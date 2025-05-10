package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.controller.GameController;
import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.exception.handlers.ServiceExceptionHandler;
import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.player.CautiousPlayer;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.model.player.RandomPlayer;
import aDarbellay.s05.t1.service.game.Dealer;
import aDarbellay.s05.t1.service.game.GameManager;
import aDarbellay.s05.t1.service.game.GameService;
import aDarbellay.s05.t1.service.player.PlayerService;
import aDarbellay.s05.t1.validation.DealingValidation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testClasses.InteractivePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Import({GameService.class, Dealer.class, Deck.class, DealingValidation.class, GameManager.class, ServiceExceptionHandler.class, PlayerFactory.class, GameController.class, PlayerService.class})
class GameServiceTest {

    @Autowired
    GameService gameService;

    static Game game;
    static List<Player> automaticPlayers;
    static List<Player> oneInteractivePlayer;
    static List<Player> interactivePlayers;
    static List<Player> mixedPlayers;


    @BeforeAll
    static void setUp() {
        game = new Game();
        Player cautiousPlayer = new CautiousPlayer();
        cautiousPlayer.setId(1);
        Player randomPlayer = new RandomPlayer();
        randomPlayer.setId(2);
        Player interactivePlayer = new InteractivePlayer();
        interactivePlayer.setId(3);
        Player secondInteractivePlayer = new InteractivePlayer();
        secondInteractivePlayer.setId(4);
        automaticPlayers = List.of(cautiousPlayer, randomPlayer);
        oneInteractivePlayer = List.of(interactivePlayer);
        interactivePlayers = List.of(interactivePlayer, secondInteractivePlayer);
        mixedPlayers = List.of(cautiousPlayer, interactivePlayer, randomPlayer, secondInteractivePlayer);
        game.setTurnsPlayed(new ArrayList<>());

    }

    @Test
    void saveGame() {
        //What happens if I try to
        Game newGame = new Game();
        newGame.setId("gameWithTwoPlayers");
        newGame.setPlayers(automaticPlayers);
        gameService.saveGame(newGame).block();
    }

    @Test
    void findGameById() {
        Mono<Game> result = gameService.getGameById("gameToTestAgain")
                .doOnNext((game) -> System.out.println(gameService.getCachedGame("gameToTestAgain")));

        StepVerifier.create(result)
                .expectNextMatches(game -> game.getId().equals("gameToTestAgain")).verifyComplete();
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
        Mono<Void> deleteResult = gameService.getGameById("gameWithTwoPlayers")
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

    @Test
    void playTurnWithAutomaticPlayers() throws IllegalActionException {
        Mono<Turn> result = gameService.startNewTurn("gameWithTwoPlayers", 0, 0)
                .doOnNext(System.out::println);
        StepVerifier.create(result).expectNextMatches(turn -> turn.getPlayerStrategies().size() == 2).verifyComplete();
        Game activeGame = gameService.getCachedGame("gameWithTwoPlayers");
        System.out.println(activeGame);
        System.out.println(activeGame.getTurnsPlayed());
        Mono<Turn> finishedResult = gameService.playTurn(activeGame.getId(), "hit", 0).
                doOnNext(turn -> {
                    System.out.println(turn);
                    System.out.println(activeGame.getTurnsPlayed());
                    gameService.saveUpdatedGame(activeGame.getId(), activeGame).block();
                });

        StepVerifier.create(finishedResult).expectNextMatches(Objects::nonNull).verifyComplete();
    }

    @Test
    void playTurnWithOnePlayer() throws IllegalActionException {
        Mono<Turn> result = gameService.startNewTurn("681c74db5fddf55897718b9e", 10, 0)
                .doOnNext(System.out::println);
        StepVerifier.create(result).consumeNextWith(turn -> {
            assertEquals(10, turn.getPlayerStrategies().getFirst().getBet());
            assertEquals(Turn.TurnState.HANDS_DISTRIBUTED, turn.getTurnState());
        }).verifyComplete();
        Game activeGame = gameService.getCachedGame("681c74db5fddf55897718b9e");
        Mono<Turn> finishedResult = gameService.playTurn(activeGame.getId(), "hit", 0).
                doOnNext(turn -> {
                    System.out.println(turn);
                    System.out.println(activeGame.getTurnsPlayed());
                    gameService.saveUpdatedGame(activeGame.getId(), activeGame).block();
                });

        StepVerifier.create(finishedResult).expectNextMatches(Objects::nonNull).verifyComplete();
    }

    @Test
    void createNewGame() {
        Mono<Game> result = gameService.createNewGame(3, oneInteractivePlayer)
                .doOnNext(game -> {
                    game.getPlayers().forEach(System.out::println);
                })
                .flatMap(game -> gameService.getGameById(game.getId()));
        StepVerifier.create(result).expectNextMatches(game -> !game.isGameOn()).verifyComplete();
    }


}