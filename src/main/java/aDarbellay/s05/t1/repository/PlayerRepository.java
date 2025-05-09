package aDarbellay.s05.t1.repository;


import aDarbellay.s05.t1.model.player.RealPlayer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface PlayerRepository extends R2dbcRepository<RealPlayer,Integer> {
    Mono<RealPlayer> findByUserName(String userName);
}
