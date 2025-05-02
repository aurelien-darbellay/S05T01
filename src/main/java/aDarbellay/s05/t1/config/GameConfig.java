package aDarbellay.s05.t1.config;

import aDarbellay.s05.t1.model.games.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GameConfig {

    @Bean
    public Map<String, Game> activeGames() {
        return new ConcurrentHashMap<>();
    }
}
