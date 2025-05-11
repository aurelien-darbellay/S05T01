package aDarbellay.s05.t1.service;


import aDarbellay.s05.t1.exception.NumPlayerOutOfBoundException;
import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.repository.GameRepository;
import aDarbellay.s05.t1.service.game.Dealer;
import aDarbellay.s05.t1.service.game.GameManager;
import aDarbellay.s05.t1.service.game.GameService;
import aDarbellay.s05.t1.validation.DealingValidation;
import aDarbellay.s05.t1.validation.ServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testClasses.InteractivePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GameServiceReactiveTest {

    @Mock
    GameRepository gameRepository;

    @Spy
    Dealer dealer = new Dealer(new Deck(), new DealingValidation());

    @Spy
    GameManager gameManager = new GameManager(new ConcurrentHashMap<>());

    @Spy
    PlayerFactory playerFactory = new PlayerFactory();

    @Spy
    ServiceValidation serviceValidation = new ServiceValidation();

    @InjectMocks
    GameService gameService;


    @Test
    void saveGame() {

        Game newGame = new Game();
        newGame.setId("fromService");
        newGame.setPlayers(List.of(new InteractivePlayer()));
        newGame.setTurnsPlayed(List.of(new Turn(1)));

        when(gameRepository.save(newGame)).thenReturn(Mono.just(newGame));
        Mono<Game> mono = gameService.saveGame(newGame);
        verify(gameRepository).save(newGame);
        StepVerifier.create(mono).expectNext(newGame).verifyComplete();
    }

    @Test
    void createGame() {

        ReflectionTestUtils.setField(serviceValidation, "numPlayerMax", 20);

        assertThat(serviceValidation.getNumPlayerMax())
                .isEqualTo(20);

        Game newGame = new Game();
        List<Player> players = new ArrayList<>();
        IntStream.range(0, 18).forEach(i -> players.add(new RealPlayer()));
        newGame.setPlayers(players);

        Mono<Game> result = gameService.createNewGame(5, players);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> assertThat(error).isInstanceOf(NumPlayerOutOfBoundException.class));

        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(newGame));
        Mono<Game> result1 = gameService.createNewGame(0, players);
        StepVerifier.create(result1)
                .expectNext(newGame)
                .verifyComplete();
    }
}
