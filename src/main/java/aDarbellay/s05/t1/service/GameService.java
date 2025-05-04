package aDarbellay.s05.t1.service;

import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.model.Player;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        Game cached = gameManager.getGameFromCache(id);
        if (cached != null) {
            return Mono.just(cached);
        }
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Game.class, id)))
                .doOnNext(gameManager::cacheGame);
    }

    public Mono<Game> saveUpdatedGame(String id, Game updatedGame) {
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

    public Mono<Turn> startNewTurn(String gameId, int bet) {
        return getGameById(gameId)
                .map(game -> dealer.startTurn(game, bet));
    }

    public Mono<Turn> playTurn(String gameId, ActionType action) {
        return getGameById(gameId)
                .map(game -> dealer.playTurn(game, action));
    }

    public Mono<Game> createNewGame(int numPlayers, Player mainPlayer) {
        List<Player> players = new ArrayList<>();
        players.add(mainPlayer);
        Collections.shuffle(players);
        Game newGame = new Game();
        newGame.setPlayers(players);
        return gameRepository.save(newGame);
    }
}
