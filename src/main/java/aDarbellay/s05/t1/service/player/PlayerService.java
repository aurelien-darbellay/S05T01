package aDarbellay.s05.t1.service.player;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class PlayerService {

    private PlayerFactory factory;
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerFactory factory, PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.factory = factory;
    }

    public Mono<Player> getPlayerByUserName(PlayerRequest request){
        return Mono.just(factory.createNewPlayer(request.getFirstName(), request.getLastName(),request.getUserName()));
    }

    public Mono<Player> savePlayer(RealPlayer player){
        return playerRepository.save(player).map(realPlayer -> (Player) realPlayer);
    }

    public Mono<Player> getPlayerByUsername(String userName){
        return playerRepository.findByUsername(userName).map(realPlayer -> (Player) realPlayer);
    }
    public Mono<Player> changePlayerNames(String userName,PlayerRequest request){
        return playerRepository.findByUsername(userName)
                .map(player ->{
                    Optional.ofNullable(request.getFirstName())
                            .filter(s -> !s.isBlank())
                            .ifPresent(player::setFirstname);
                    Optional.ofNullable(request.getLastName())
                            .filter(s -> !s.isBlank())
                            .ifPresent(player::setLastname);
                    Optional.ofNullable(request.getUserName())
                            .filter(s -> !s.isBlank())
                            .ifPresent(player::setUsername);
                    return player;
                })
                .flatMap(realPlayer -> playerRepository.save(realPlayer))
                .map(realplayer -> (Player) realplayer);
    }

}
