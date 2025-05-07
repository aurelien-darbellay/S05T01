package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.model.player.RealPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@Service
class PlayerServiceTest {
    static PlayerFactory playerFactory = new PlayerFactory();
    static PlayerService playerService = new PlayerService(playerFactory);

    @Test
    void getPlayerByName() {
        PlayerRequest request = new PlayerRequest();
        request.setFirstName("Aurélien");
        request.setLastName("Darbellay");
        Mono<Player> result = playerService.getPlayerByName(request);
        assertNotNull(result);
        StepVerifier.create(result).consumeNextWith(player -> {
            assertEquals("Darbellay", ((RealPlayer) player).getLastname());
        }).verifyComplete();

    }
}