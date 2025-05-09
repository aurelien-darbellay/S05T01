package aDarbellay.s05.t1.service.player;


import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RankingManager {

    private GameService gameService;

    @Autowired
    public RankingManager(GameService gameService){
        this.gameService = gameService;
    }

    public Flux<Player> calculatePlayersPoints(Flux<RealPlayer> fluxPlayers){
        return fluxPlayers.flatMap(player -> gameService.getAllGame()
                .filter(game->gameHasPlayer(game, player))
                .flatMapIterable(Game::getTurnsPlayed)
                .doOnNext(turn-> calculatePlayerPoints(turn,player))
                .then(Mono.just(player)));
    }

    private void calculatePlayerPoints(Turn turn, RealPlayer player){
        turn.getPlayerStrategies().stream()
                .filter(playerStrategy -> playerHasStrategy(player,playerStrategy))
                .forEach(playerStrategy->calculatePlayerPoints(playerStrategy,player));
    }
    private void calculatePlayerPoints(PlayerStrategy playerStrategy, RealPlayer player) {
        switch (playerStrategy.getResult()){
            case LOSS -> player.addPoints(-playerStrategy.getBet());
            case WIN -> player.addPoints(playerStrategy.getBet());
        }
    }
    private boolean playerHasStrategy(RealPlayer player, PlayerStrategy playerStrategy){
        return playerStrategy.getId() == player.getId() || playerStrategy.getId()%(player.getId()*10)==0;
    }

    private boolean gameHasPlayer(Game game, RealPlayer player){
        return game.getPlayers().stream()
                .anyMatch(playerInGame-> ((playerInGame instanceof RealPlayer) && ((RealPlayer)playerInGame).getUsername().equals(player.getUsername())));

    }
}
