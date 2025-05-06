package aDarbellay.s05.t1.repository;

import aDarbellay.s05.t1.model.games.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import aDarbellay.s05.t1.model.player.RandomPlayer;

import java.util.List;

@DataMongoTest
class GameRepositoryTest {
    @Autowired
    GameRepository gameRepository;

    @Test
    void saveGame() {
        Game newGame = new Game();
        newGame.setPlayers(List.of(new RandomPlayer()));
        gameRepository.save(newGame).block();
    }

    @Test
    void findGame() {

    }

}