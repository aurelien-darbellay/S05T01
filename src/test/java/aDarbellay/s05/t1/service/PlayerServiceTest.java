package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.service.player.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@Service
class PlayerServiceTest {

    @Autowired
    PlayerService playerService;

    @Test
    void getPlayerByUserName() {
        PlayerRequest request = new PlayerRequest();
        request.setFirstName("Aurélien");
        request.setLastName("Darbellay");
        Mono<Player> result = playerService.getPlayerByUserName(request);
        assertNotNull(result);
        StepVerifier.create(result).consumeNextWith(player -> {
            assertEquals("Darbellay", ((RealPlayer) player).getLastname());
        }).verifyComplete();

    }
}