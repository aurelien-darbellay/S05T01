package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final Dealer dealer;
    private final GameManager gameManager;

    @Autowired
    public GameService(GameRepository gameRepository, Dealer dealer, GameManager gameManager) {
        this.gameRepository = gameRepository;
        this.dealer = dealer;
        this.gameManager = gameManager;
    }

    public Mono<Game> saveGame(Game game) {
        return gameRepository.save(game);
    }

    public Mono<Game> getGameById(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Game.class, id)));
    }

    public Mono<Game> updateGame(String id, Game updatedGame) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Game.class, id)))
                .map(game -> {
                    game.setPlayers(updatedGame.getPlayers());
                    game.setTurnsPlayed(updatedGame.getTurnsPlayed());
                    game.setActiveTurn(updatedGame.getActiveTurn());
                    return game;
                })
                .flatMap(gameRepository::save);
    }

    public Mono<Void> deleteGame(Game game) {
        return gameRepository.delete(game);
    }

    public Mono<Void> deleteById(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Game.class, id)))
                .flatMap(game -> gameRepository.deleteById(id));
    }

    public Flux<Game> getAllGame() {
        return gameRepository.findAll();
    }

}
