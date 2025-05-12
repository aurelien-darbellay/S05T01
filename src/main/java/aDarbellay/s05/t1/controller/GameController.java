package aDarbellay.s05.t1.controller;


import aDarbellay.s05.t1.dto.responseDTO.DTOMapper;
import aDarbellay.s05.t1.dto.responseDTO.GameDTO;
import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.dto.responseDTO.TurnDTO;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.service.game.GameService;
import aDarbellay.s05.t1.service.player.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping
    public Flux<GameDTO> getGame() {
        return gameService.getAllGame().map(DTOMapper::gameMapper);
    }

    @GetMapping("/{id}")
    public Mono<GameDTO> getGame(@PathVariable String id) {
        return gameService.getGameById(id).map(DTOMapper::gameMapper);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameDTO> createGame(@RequestBody @Valid PlayerRequest playerRequest) {
        return playerService.getOrCreatePlayer(playerRequest)
                .doOnNext(player -> System.out.println("DEBUG: Retrieved player = " + player))
                .flatMap(player -> gameService.createNewGame(0, List.of(player)))
                .map(DTOMapper::gameMapper);
    }

    @PostMapping("/{id}/bet")
    public Mono<TurnDTO> startTurn(@PathVariable String id, @RequestParam int bet, @RequestParam String username) {
        return playerService.getPlayerId(username)
                .flatMap(playerId -> gameService.startNewTurn(id, bet, playerId))
                .map(DTOMapper::turnMapper);
    }

    @PostMapping("/{id}/play")
    public Mono<TurnDTO> playTurn(@PathVariable String id, @RequestParam String actionType, @RequestParam String username) {
        return playerService.getPlayerId(username)
                .flatMap(playerId -> gameService.playTurn(id, actionType, playerId))
                .map(DTOMapper::turnMapper);
    }

    @PostMapping("/{id}/save")
    public Mono<GameDTO> saveGame(@PathVariable String id) {
        Game game = gameService.getCachedGame(id);
        return gameService.saveUpdatedGame(id, game).map(DTOMapper::gameMapper);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGameById(@PathVariable String id) {
        return gameService.deleteById(id);
    }


}
