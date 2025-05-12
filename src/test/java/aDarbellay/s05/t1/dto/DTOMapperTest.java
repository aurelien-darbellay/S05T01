package aDarbellay.s05.t1.dto;

import aDarbellay.s05.t1.dto.responseDTO.DTOMapper;
import aDarbellay.s05.t1.dto.responseDTO.TurnDTO;
import aDarbellay.s05.t1.model.actions.Stand;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.PlayerStrategyBuilder;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.games.TurnBuilder;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.CautiousPlayer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DTOMapperTest {

    @Test
    void turnMapper() {
        PlayerStrategy strategy1 = new PlayerStrategyBuilder()
                .setPlayer(new CautiousPlayer())
                .setBet(10)
                .setHand(new Hand())
                .setResult(PlayerStrategy.ResultType.LOSS)
                .setActions(List.of(new Stand()))
                .build();
        Turn newTurn = new TurnBuilder()
                .setPlayerStrategies(List.of(strategy1))
                .setTurnState(Turn.TurnState.TURN_FINISHED)
                .build();
        TurnDTO turnDTO = DTOMapper.turnMapper(newTurn);
        assertEquals(DTOMapper.strategyMapper(strategy1), turnDTO.getPlayerStrategies().getFirst());
        assertEquals(10, turnDTO.getPlayerStrategies().getFirst().getBet());
    }
}