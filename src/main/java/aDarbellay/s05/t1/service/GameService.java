package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GameService {

    private GameRepository gameRepository;
    private Dealer dealer;
    private GameManager gameManager;

    @Autowired
    public GameService(GameRepository gameRepository, Dealer dealer, GameManager gameManager) {
        this.gameRepository = gameRepository;
        this.dealer = dealer;
        this.gameManager = gameManager;
    }

    public Mono<Game> saveGame(Game game) {
        return gameRepository.save(game);
    }

    public Mono<Game> findGameById(String id) {
        return gameRepository.findById(id);
    }

    public Mono<Game> updateGame(String id, Game updatedGame) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setPlayers(updatedGame.getPlayers());
                    game.setTurnsPlayed(updatedGame.getTurnsPlayed());
                    game.setActiveTurn(updatedGame.getActiveTurn());
                    return game;
                })
                .flatMap(gameRepository::save);
    }

}
