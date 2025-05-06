package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.model.games.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameManager {

    final private Map<String, Game> activeGames;

    @Autowired
    public GameManager(@Qualifier("activeGames") Map<String, Game> emptyMap) {
        this.activeGames = emptyMap;
    }

    public void cacheGame(Game game) {
        activeGames.put(game.getId(), game);
    }

    public Game getGameFromCache(String id) {
        return activeGames.get(id);
    }

    public void dropGameFromCache(String id) {
        activeGames.remove(id);
    }
}
