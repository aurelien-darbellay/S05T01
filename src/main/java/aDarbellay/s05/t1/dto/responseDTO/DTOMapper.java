package aDarbellay.s05.t1.dto.responseDTO;

import aDarbellay.s05.t1.model.cards.Card;
import aDarbellay.s05.t1.model.games.Game;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.games.Turn;
import aDarbellay.s05.t1.model.player.Player;

public class DTOMapper {
    public static PlayerDTO playerMapper(Player player){
        return isNotNull(player) ?
                new PlayerDTO(player.getUserName(), player.getFirstName(), player.getLastName(),player.getPoints())
                : null;
    }
    public static PlayerStrategyDTO strategyMapper(PlayerStrategy strategy){
        return isNotNull(strategy) ?
                new PlayerStrategyDTO(
                playerMapper(strategy.getPlayer()),
                strategy.getBet(),
                isNotNull(strategy.getActions())
                        ? strategy.getActions().stream().map(action->action.getClass().getSimpleName()).toList()
                        : null,
                isNotNull(strategy.getHand())
                        ? strategy.getHand().stream().map(Card::getValue).toList()
                        : null,
                isNotNull(strategy.getResult())
                        ? strategy.getResult().getValue()
                        :null
        ) : null;
    }
    public static TurnDTO turnMapper(Turn turn){
        return isNotNull(turn) ?
                new TurnDTO(
                turn.getId(),
                isNotNull(turn.getPlayerStrategies())
                        ? turn.getPlayerStrategies().stream().map(DTOMapper::strategyMapper).toList()
                        :null,
                isNotNull(turn.getDealerHand())
                        ? turn.getDealerHand().stream().map(Card::getValue).toList()
                        :null,
                isNotNull(turn.getTurnState())
                        ? turn.getTurnState().getValue()
                        :null,
                turn.isInputRequired()
        ) : null;
    }
    public static GameDTO gameMapper(Game game){
        return new GameDTO(
                game.getId(),
                game.getPlayers().stream().map(DTOMapper::playerMapper).toList(),
                game.getTurnsPlayed().stream().map(DTOMapper::turnMapper).toList(),
                DTOMapper.turnMapper(game.getActiveTurn()),
                game.isGameOn()
        );
    }
    static boolean isNotNull(Object o){
        return o !=null;
    }
}
