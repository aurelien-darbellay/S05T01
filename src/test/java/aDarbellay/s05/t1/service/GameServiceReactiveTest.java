package aDarbellay.s05.t1.service;


import aDarbellay.s05.t1.model.cards.Deck;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.repository.GameRepository;
import aDarbellay.s05.t1.validation.DealingValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testClasses.InteractivePlayer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
}
