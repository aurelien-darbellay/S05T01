package aDarbellay.s05.t1.service.player;

import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class RankingManager {

    private GameService gameService;

    @Autowired
    public RankingManager(GameService gameService){
        this.gameService = gameService;
    }

    public Flux<Player> calculatePlayersPoints(Flux<RealPlayer> fluxPlayers){
        Flux<Game> fluxGames = gameService.getAllGame()
                .doOnNext(game ->{
                    fluxPlayers.doOnNext(player -> {
                        if (gameHasPlayer(game, player)){
                            game.getTurnsPlayed().forEach(turn -> calculatePlayerPoints(turn, player));
                        }
                    })
                })
    }

    private void calculatePlayerPoints(Turn turn, RealPlayer player){

    }

    private boolean gameHasPlayer(Game game, RealPlayer player){
        return game.getPlayers().stream()
                .anyMatch(playerInGame-> ((playerInGame instanceof RealPlayer) && ((RealPlayer)playerInGame).getUsername().equals(player.getUsername())));

    }
}
