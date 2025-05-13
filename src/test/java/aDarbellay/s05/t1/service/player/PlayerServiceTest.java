package aDarbellay.s05.t1.service.player;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.model.actions.Stand;
import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.*;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.repository.PlayerRepository;
import aDarbellay.s05.t1.service.game.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerFactory factory;

    @MockitoBean
    GameService gameService;

    @MockitoBean
    PlayerRepository playerRepository;

    @Test
    void test1() {
        PlayerRequest request = new PlayerRequest();
        request.setFirstName("Aurélien");
        request.setLastName("Darbellay");
        request.setUserName("auda");
        RealPlayer pl = new RealPlayer();
        pl.setLastName("Darbellay");
        when(playerRepository.findByUserName("auda")).thenReturn(Mono.just(pl));
        when(playerRepository.save(any(RealPlayer.class))).thenReturn(Mono.just(pl));
        Mono<Player> result = playerService.getOrCreatePlayer(request);
        assertNotNull(result);
        StepVerifier.create(result).consumeNextWith(player -> {
            assertEquals("Darbellay", ((RealPlayer) player).getLastName());
        }).verifyComplete();

    }

    @Test
    void savePlayer() {

        RealPlayer player1 = factory.createNewPlayer("Seth", "dada", "Seda");
        when(playerRepository.save(any(RealPlayer.class))).thenReturn(Mono.just(player1));
        Mono<Player> result = playerService.savePlayer(player1);
        StepVerifier.create(result)
                .expectNextMatches(player -> player.getFirstName().equals("Seth"))
                .verifyComplete();
    }

    @Test
    void getPlayerByUsername() {
        when(playerRepository.findByUserName(any(String.class))).thenReturn(Mono.empty());
        Mono<Player> result = playerService.getPlayerByUsername("audi");
        assertInstanceOf(Mono.class, result);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getPlayerByUsernameFound() {
        when(playerRepository.findByUserName("audi")).thenReturn(Mono.just(factory.createNewPlayer("a", "ta", "ata")));
        Mono<Player> result = playerService.getPlayerByUsername("audi");
        assertInstanceOf(Mono.class, result);
        StepVerifier.create(result)
                .expectNextMatches(player -> player.getFirstName().equals("a"))
                .verifyComplete();
    }

    @Test
    void getOrCreatePlayer() {
        PlayerRequest request = new PlayerRequest();
        request.setUserName("ata");
        request.setLastName("o");
        request.setFirstName("b");

        // Player already exists
        when(playerRepository.findByUserName("ata"))
                .thenReturn(Mono.just(factory.createNewPlayer("a", "ta", "ata")));

        Mono<Player> result = playerService.getOrCreatePlayer(request);

        StepVerifier.create(result)
                .expectNextMatches(player -> player.getUserName().equals("ata"))
                .verifyComplete();

        verify(playerRepository).findByUserName("ata");
        verify(playerRepository, never()).save(any(RealPlayer.class)); // Should NOT be called
    }

    @Test
    void changePlayerNames() {
        PlayerRequest request1 = new PlayerRequest();
        request1.setUserName("Alon");
        request1.setFirstName("Assumpto");
        request1.setLastName("Pascualina");
        when(playerRepository.findByUserName("Asun"))
                .thenReturn(Mono.just(factory.createNewPlayer("a", "ta", "Asun")));
        when(playerRepository.save(any(RealPlayer.class))).thenReturn(Mono.just(factory.createNewPlayer("Assumpto", "Pascualina", "Asun")));
        Mono<Player> result = playerService.changePlayerNames("Asun", request1);
        StepVerifier.create(result)
                .expectNextMatches(player -> player.getFirstName().equals("Assumpto"));
    }

    @Test
    void updatePlayersPoints() {
    }

    @Test
    void getPlayersRanking() {
        RealPlayer player1 = factory.createNewPlayer("Lola", "Mateos", "LolaMa");
        PlayerStrategy strategy11 = new PlayerStrategyBuilder()
                .setPlayer(player1).setActions(List.of(new Stand())).setBet(10).setHand(Hand.createPlayerHand(List.of(new Card("H8"), new Card("S10"))))
                .setTurn(1).setResult(PlayerStrategy.ResultType.LOSS).build();
        Turn turn11 = new TurnBuilder().setId(1).setPlayerStrategies(List.of(strategy11)).setGameId("1").setTurnState(Turn.TurnState.TURN_FINISHED).build();
        Game game1 = new GameBuilder().setId("1").setPlayers(List.of(player1)).setTurnsPlayed(List.of(turn11)).build();

        RealPlayer player2 = factory.createNewPlayer("Pedro", "Mateos", "PedroMa");
        PlayerStrategy strategy21 = new PlayerStrategyBuilder()
                .setPlayer(player1).setActions(List.of(new Stand())).setBet(10).setHand(Hand.createPlayerHand(List.of(new Card("H8"), new Card("S10"))))
                .setTurn(1).setResult(PlayerStrategy.ResultType.WIN).build();
        Turn turn21 = new TurnBuilder().setId(1).setPlayerStrategies(List.of(strategy21)).setGameId("2").setTurnState(Turn.TurnState.TURN_FINISHED).build();
        Game game2 = new GameBuilder().setId("2").setPlayers(List.of(player2)).setTurnsPlayed(List.of(turn21)).build();

        when(gameService.getAllGame()).thenReturn(Flux.fromIterable(List.of(game1, game2)));
        when(playerRepository.findAll()).thenReturn(Flux.fromIterable(List.of(player1, player2)));
        when(playerRepository.save(player1)).thenReturn(Mono.just(player1));
        when(playerRepository.save(player2)).thenReturn(Mono.just(player2));

        Flux<Player> result = playerService.getPlayersRanking();
        StepVerifier.create(result)
                .expectNextMatches(player -> player.getFirstName().equals("Pedro"))
                .expectNextMatches(player -> player.getFirstName().equals("Lola"))
                .verifyComplete();

        verify(playerRepository).save(player1);
        verify(playerRepository).save(player2);

    }
}