package aDarbellay.s05.t1.controller;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class RootController {

    PlayerService playerService;

    @Autowired
    public RootController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PutMapping("/player/{userName}")
    public Mono<Player> updatePlayerNames(@PathVariable String userName, @RequestBody PlayerRequest request) {
        return playerService.changePlayerNames(userName, request);
    }

    @GetMapping("/ranking")
    public Flux<Player> getPlayersRanking() {
        return playerService.getPlayersRanking();
    }

}
