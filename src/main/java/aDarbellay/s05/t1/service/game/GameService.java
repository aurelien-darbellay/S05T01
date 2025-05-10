package aDarbellay.s05.t1.service.game;

import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.exception.handlers.ServiceExceptionHandler;
import aDarbellay.s05.t1.model.Bet;
import aDarbellay.s05.t1.model.actions.ActionChoice;
import aDarbellay.s05.t1.model.actions.ActionType;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final Dealer dealer;
    private final GameManager gameManager;
    private final ServiceExceptionHandler serviceExceptionHandler;
    private final PlayerFactory playerFactory;

    @Autowired
    public GameService(GameRepository gameRepository, Dealer dealer, GameManager gameManager, ServiceExceptionHandler serviceExceptionHandler, PlayerFactory playerFactory) {
        this.gameRepository = gameRepository;
        this.dealer = dealer;
        this.gameManager = gameManager;
        this.serviceExceptionHandler = serviceExceptionHandler;
        this.playerFactory = playerFactory;
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
        updatedGame.setId(id);
        return saveGame(updatedGame);
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

    public Mono<Turn> startNewTurn(String gameId, int betValue, int strategyId) {
        Bet bet = new Bet(betValue);
        return getGameById(gameId)
                .flatMap(game -> serviceExceptionHandler.exceptionPropagator(game, bet, strategyId, dealer::startTurn));
    }

    public Mono<Turn> playTurn(String gameId, String actionType, int strategyId) throws IllegalActionException {
        ActionChoice actionChoice = new ActionChoice();
        actionChoice.setActionType(ActionType.matchStringToActionType(actionType));
        return getGameById(gameId)
                .flatMap(game -> serviceExceptionHandler.exceptionPropagator(game, actionChoice, strategyId, dealer::playTurn));
    }


    public Mono<Game> createNewGame(int numAutomaticPlayers, List<Player> interactivePlayers) {
        List<Player> players = new ArrayList<>(interactivePlayers);
        IntStream.range(1, numAutomaticPlayers).forEach(num -> players.add(playerFactory.createNewPlayer()));
        Collections.shuffle(players);
        Game newGame = new Game();
        newGame.setPlayers(players);
        return gameRepository.save(newGame);
    }

    public Game getCachedGame(String id) {
        return gameManager.getGameFromCache(id);
    }
}
