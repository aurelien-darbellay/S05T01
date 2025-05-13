package aDarbellay.s05.t1.repository;

import aDarbellay.s05.t1.model.player.RealPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class PlayerRepositoryTest {

    @Autowired
    PlayerRepository playerRepository;

    @Test
    void findByUserName() {
        RealPlayer pl = new RealPlayer();
        pl.setFirstName("Test");
        pl.setLastName("Player");
        pl.setUserName("Repository");
        Mono<RealPlayer> result1 = playerRepository.save(pl);
        StepVerifier.create(result1)
                .expectNextMatches(realPlayer -> {
                    assertNotEquals(0, realPlayer.getId());
                    return realPlayer.getUserName().equals("Repository");
                }).verifyComplete();
        Mono<RealPlayer> result2 = playerRepository.findByUserName("Repository")
                .flatMap(realPlayer -> {
                    assertNotEquals(0, realPlayer.getId());
                    return playerRepository.delete(realPlayer)
                            .thenReturn(realPlayer);
                });
        StepVerifier.create(result2)
                .expectNextMatches(realPlayer -> realPlayer.getUserName().equals("Repository")).verifyComplete();
    }
}