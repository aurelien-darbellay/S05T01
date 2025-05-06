package aDarbellay.s05.t1.controller;


import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.player.RandomPlayer;
import aDarbellay.s05.t1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }
    @GetMapping
    public Flux<Game> getGames(){
        return gameService.getAllGame();
    }

    @PostMapping ("/new")
    public Mono<Game> createGame() {
        return gameService.createNewGame(1, List.of(new RandomPlayer()));
    }


}
