package aDarbellay.s05.t1.service.player;

import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.model.player.RealPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerFactory factory;

    @Test
    void getPlayerByUserName() {
    }

    @Test
    void savePlayer() {
        RealPlayer player = factory.createNewPlayer("aure","dada","adada");
    }

    @Test
    void getPlayerByUsername() {
    }

    @Test
    void changePlayerNames() {
    }

    @Test
    void updatePlayersPoints() {
    }

    @Test
    void getPlayersRanking() {
    }
}