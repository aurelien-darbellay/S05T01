package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {

    private PlayerFactory factory;

    @Autowired
    public PlayerService(PlayerFactory factory) {
        this.factory = factory;
    }

    public Mono<Player> getPlayerByName(PlayerRequest request){
        return Mono.just(factory.createNewPlayer(request.getFirstName(), request.getLastName()));
    }
}
