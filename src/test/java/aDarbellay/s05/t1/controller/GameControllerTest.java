package aDarbellay.s05.t1.controller;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.model.actions.Stand;
import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.service.GameService;
import aDarbellay.s05.t1.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@WebFluxTest(GameController.class)
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
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
                    assertEquals(mockGame, game);
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
        when(gameService.createNewGame(0, List.of(player))).thenReturn(Mono.just(game));
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
    void startTurn() {
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
        newTurn.setTurnState(Turn.TurnState.HANDS_DISTRIBUTED);
        PlayerStrategy newPlayerStrategy = new PlayerStrategy(1, player);
        newPlayerStrategy.setBet(10);
        newTurn.setPlayerStrategies(List.of(newPlayerStrategy));

        when(gameService.startNewTurn("11", 10, 1)).thenReturn(Mono.just(newTurn));
        webTestClient.post()
                .uri("/game/11/bet?bet=10&&playerId=1")
                .exchange()
                .expectBody(Turn.class)
                .consumeWith(turnEntityExchangeResult -> {
                    Turn turn = turnEntityExchangeResult.getResponseBody();
                    assertEquals(10, turn.getPlayerStrategies().getFirst().getBet());
                    assertEquals(Turn.TurnState.HANDS_DISTRIBUTED, turn.getTurnState());
                });

    }

    @Test
    void playTurn() throws IllegalActionException {

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
        newTurn.setTurnState(Turn.TurnState.TURN_FINISHED);
        newTurn.setInputRequired(true);

        Hand dealerHand = new Hand();
        dealerHand.add(new Card("H7"));
        dealerHand.add(new Card("SJ"));
        newTurn.setDealerHand(dealerHand);

        PlayerStrategy newPlayerStrategy = new PlayerStrategy(1, player);
        newPlayerStrategy.setBet(10);
        Hand playerHand = new Hand();
        playerHand.addAll(List.of(new Card("S2"), new Card("HJ")));
        newPlayerStrategy.setHand(playerHand);
        newPlayerStrategy.setActions(List.of(new Stand()));
        newTurn.setPlayerStrategies(List.of(newPlayerStrategy));

        when(gameService.playTurn("11", "stand", 1)).thenReturn(Mono.just(newTurn));
        webTestClient.post()
                .uri("/game/11/play?actionType=stand&&playerId=1")
                .exchange()
                .expectBody(Turn.class)
                .consumeWith(turnEntityExchangeResult -> {
                    Turn turn = turnEntityExchangeResult.getResponseBody();
                    assert turn != null;
                    assertEquals(Turn.TurnState.TURN_FINISHED, turn.getTurnState());
                    assertInstanceOf(Stand.class, turn.getPlayerStrategies().getFirst().getActions().getFirst());
                });

    }
}