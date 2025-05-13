package aDarbellay.s05.t1.service.player;

import aDarbellay.s05.t1.dto.PlayerRequest;
import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.model.player.Player;
import aDarbellay.s05.t1.model.player.PlayerFactory;
import aDarbellay.s05.t1.model.player.RealPlayer;
import aDarbellay.s05.t1.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerFactory factory;
    private final PlayerRepository playerRepository;
    private final RankingManager rankingManager;

    @Autowired
    public PlayerService(PlayerFactory factory, PlayerRepository playerRepository, RankingManager rankingManager) {
        this.playerRepository = playerRepository;
        this.factory = factory;
        this.rankingManager = rankingManager;
    }

    public Mono<Player> getOrCreatePlayer(PlayerRequest request) {
        return getPlayerByUsername(request.getUserName())
                .switchIfEmpty(Mono.defer(() -> savePlayer(factory.createNewPlayer(request.getFirstName(), request.getLastName(), request.getUserName()))));

    }

    public Mono<Player> getPlayerOrFail(PlayerRequest request) {
        return getPlayerByUsername(request.getUserName())
                .switchIfEmpty(Mono.error(new EntityNotFoundException(RealPlayer.class, request.getUserName())));
    }

    public Mono<Player> savePlayer(RealPlayer player) {
        System.out.println("Empty");
        return playerRepository.save(player).map(realPlayer -> (Player) realPlayer);
    }

    public Mono<Player> getPlayerByUsername(String userName) {
        return playerRepository.findByUserName(userName)
                .map(realPlayer -> (Player) realPlayer);
    }

    public Mono<Integer> getPlayerId(String username) {
        return getPlayerByUsername(username).map(Player::getId);
    }

    public Mono<Player> changePlayerNames(String userName, PlayerRequest request) {
        return playerRepository.findByUserName(userName)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(RealPlayer.class, userName)))
                .map(player -> {
                    Optional.ofNullable(request.getFirstName())
                            .filter(s -> !s.isBlank())
                            .ifPresent(player::setFirstName);
                    Optional.ofNullable(request.getLastName())
                            .filter(s -> !s.isBlank())
                            .ifPresent(player::setLastName);
                    Optional.ofNullable(request.getUserName())
                            .filter(s -> !s.isBlank())
                            .ifPresent(player::setUserName);
                    return player;
                })
                .flatMap(playerRepository::save)
                .map(realplayer -> (Player) realplayer);
    }

    public Flux<Player> updatePlayersPoints() {
        return rankingManager
                .calculatePlayersPoints(playerRepository.findAll())
                .flatMap(player -> playerRepository.save((RealPlayer) player));
    }

    public Flux<Player> getPlayersRanking() {
        return updatePlayersPoints()
                .collectSortedList(Comparator.comparing(Player::getPoints).reversed())
                .flatMapMany(Flux::fromIterable);
    }

}
