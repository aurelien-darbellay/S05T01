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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class PlayerServiceTest {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerFactory factory;

    @MockitoBean
    GameService gameService;

    @MockitoBean
    PlayerRepository mockPlayerRepository;

    @Test
    void test1() {
        PlayerRequest request = new PlayerRequest();
        request.setFirstName("Aurélien");
        request.setLastName("Darbellay");
        Mono<Player> result = playerService.getOrCreatePlayer(request);
        assertNotNull(result);
        StepVerifier.create(result).consumeNextWith(player -> {
            assertEquals("Darbellay", ((RealPlayer) player).getLastName());
        }).verifyComplete();

    }

    @Test
    void savePlayer() {
        RealPlayer player = factory.createNewPlayer("aure", "dada", "auda");
        RealPlayer player1 = factory.createNewPlayer("Seth", "dada", "Seda");
        RealPlayer player2 = factory.createNewPlayer("Atahualpa", "Yupanki", "ata");
        RealPlayer player3 = factory.createNewPlayer("Mohammed", "Khalil", "Moka");
        List.of(player1, player2, player3, player)
                .forEach(pl -> playerService.savePlayer(pl).block());
    }

    @Test
    void getPlayerByUsername() {
        Mono<Player> result = playerService.getPlayerByUsername("audi");
        assertInstanceOf(Mono.class, result);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getOrCreatePlayer() {
        PlayerRequest request = new PlayerRequest();
        request.setUserName("ata");
        Mono<Player> result = playerService.getOrCreatePlayer(request);
        StepVerifier.create(result)
                .expectNextMatches(player -> player.getUserName().equals("ata"))
                .verifyComplete();
        PlayerRequest request1 = new PlayerRequest();
        request1.setUserName("Asu");
        request1.setFirstName("Assumpta");
        request1.setLastName("Pascual");
        Mono<Player> result1 = playerService.getOrCreatePlayer(request1);
        StepVerifier.create(result1)
                .expectNextMatches(player -> player.getId() == 6)
                .verifyComplete();

    }

    @Test
    void changePlayerNames() {
        PlayerRequest request1 = new PlayerRequest();
        request1.setUserName("Asun");
        request1.setFirstName("Assumpto");
        request1.setLastName("Pascualina");
        playerService.changePlayerNames("Asun", request1).block();
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
        when(mockPlayerRepository.findAll()).thenReturn(Flux.fromIterable(List.of(player1, player2)));
        when(mockPlayerRepository.save(player1)).thenReturn(Mono.just(player1));
        when(mockPlayerRepository.save(player2)).thenReturn(Mono.just(player2));

        Flux<Player> result = playerService.getPlayersRanking();
        StepVerifier.create(result)
                .expectNextMatches(player -> player.getFirstName().equals("Pedro"))
                .expectNextMatches(player -> player.getFirstName().equals("Lola"))
                .verifyComplete();

        verify(mockPlayerRepository).save(player1);
        verify(mockPlayerRepository).save(player2);

    }
}