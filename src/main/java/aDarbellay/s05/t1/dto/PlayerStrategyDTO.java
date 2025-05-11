package aDarbellay.s05.t1.dto;

import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.games.PlayerStrategy;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerStrategyDTO {
    private int turn;
    private Player player;
    private Integer bet;
    private List<Action> actions = new ArrayList<>();
    private Hand hand;
    private PlayerStrategy.ResultType result;
}
