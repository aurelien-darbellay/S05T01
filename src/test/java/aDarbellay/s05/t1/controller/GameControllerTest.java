package aDarbellay.s05.t1.controller;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.service.GameService;
import aDarbellay.s05.t1.service.PlayerService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(GameController.class)
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;


    @Test
    void testGetGameById() {
        Game mockGame = new Game();
        mockGame.setId("id123");
        when(gameService.getGameById("id123")).thenReturn(Mono.just(mockGame));

        webTestClient.get()
                .uri("/game/id123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Game.class)
                .consumeWith(gameEntityExchangeResult -> {
                    Game game = gameEntityExchangeResult.getResponseBody();
                    assertEquals(mockGame,game);
                    System.out.println(game.getPlayers());
                });
    }
    @Test
    void createGame() {
        PlayerRequest request = new PlayerRequest();
        request.setFirstName("Aurélien");
        request.setLastName("Darbellay");
        RealPlayer player = new RealPlayer();
        player.setFirstname("Aurélien");
        player.setLastname("Darbellay");
        Game game = new Game();
        game.setPlayers(List.of(player));
        when(playerService.getPlayerByName(request)).thenReturn(Mono.just((Player) player));
        when(gameService.createNewGame(0,List.of(player))).thenReturn(Mono.just(game));
        webTestClient.post()
                .uri("/game/new")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Game.class)
                .consumeWith(gameEntityExchangeResult -> {
                    Game returnedGame = gameEntityExchangeResult.getResponseBody();
                    assertEquals("Aurélien", ((RealPlayer) returnedGame.getPlayers().getFirst()).getFirstname());
                });

    }

    @Test
    void startTurn(){
        RealPlayer player = new RealPlayer();
        player.setFirstname("Aurélien");
        player.setLastname("Darbellay");
        player.setId(1);
        Game game = new Game();
        game.setPlayers(List.of(player));
        game.setId("11");
        PlayerRequest request = new PlayerRequest();
        request.setFirstName("Aurélien");
        request.setLastName("Darbellay");
        Turn newTurn = new Turn(1);
        PlayerStrategy newPlayerStrategy = new PlayerStrategy(1,player);
        newPlayerStrategy.setBet(10);
        newTurn.setPlayerStrategies(List.of(newPlayerStrategy));

        when(gameService.startNewTurn("11",10,1)).thenReturn(Mono.just(newTurn));
        webTestClient.post()
                .uri("/game/11/bet?bet=10&&playerId=1")
                .exchange()
                .expectBody(Turn.class)
                .consumeWith(turnEntityExchangeResult -> {
                   Turn turn = turnEntityExchangeResult.getResponseBody();
                   assertEquals(10,turn.getPlayerStrategies().getFirst().getBet());
                });

    }
}