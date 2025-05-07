package aDarbellay.s05.t1.controller;


import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.service.GameService;
import aDarbellay.s05.t1.service.PlayerService;
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
    public Flux<Game> getGame(){
        return gameService.getAllGame();
    }
    @GetMapping("/{id}")
    public Mono<Game> getGame(@PathVariable String id ){
        return gameService.getGameById(id);
    }

    @PostMapping ("/new")
    @ResponseStatus (HttpStatus.CREATED)
    public Mono<Game> createGame(@RequestBody PlayerRequest playerRequest) {
        return playerService.getPlayerByName(playerRequest)
                .doOnNext(player -> System.out.println("DEBUG: Retrieved player = " + player))
                .flatMap(player -> gameService.createNewGame(0, List.of(player)));
    }

    @PostMapping("/{id}/bet")
    public Mono<Turn> startTurn(@PathVariable String id, @RequestParam int bet,@RequestParam int playerId){
        return gameService.startNewTurn(id, bet, playerId);
    }
    @PostMapping("/{id}/play")
    public Mono<Turn> playTurn(@PathVariable String id, @RequestParam String actionType,@RequestParam int playerId) throws IllegalActionException {
        return gameService.playTurn(id, actionType, playerId);
    }


}
