package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.hands.Hand;
import aDarbellay.s05.t1.model.hands.Hand.HandType;
import aDarbellay.s05.t1.model.hands.Hand.Visibility;

import java.util.*;


public class Dealer {

    private final Game game;
    private boolean gameOn;
    private List<Card> fullDeck;

    public Dealer(Game game, List<Card> fullDeck) {
        this.game = game;
        this.gameOn = false;
        this.fullDeck = fullDeck;
    }

    public Turn runTurn() {
        setGameOn(true);
        List<Player> players = game.getPlayers();
        Turn newTurn = initializeNewTurn(game);
        takeBets(newTurn);
        distributeCard(newTurn);
        invitePlayersToPlayHand(newTurn);

        game.getTurnsPlayed().add(newTurn);
        setGameOn(false);
        return newTurn;
    }

    private Turn initializeNewTurn(Game game) {
        Turn newTurn = new Turn(game.getTurnsPlayed().size() + 1);
        List<PlayerTurn> playerTurns = createPlayerTurns(newTurn, game.getPlayers());
        newTurn.setPlayerTurns(playerTurns);
        return newTurn;
    }

    private List<PlayerTurn> createPlayerTurns(Turn turn, List<Player> players) {
        return players.stream().map(player -> new PlayerTurn(turn.getId(), player)).toList();
    }

    private void takeBets(Turn turn) {
        turn.getPlayerTurns().forEach(playerTurn -> playerTurn.getPlayer().placeBet(playerTurn));
    }

    private void distributeCard(Turn turn) {
        Collections.shuffle(fullDeck);
        turn.setReserve(fullDeck);
        turn.getPlayerTurns().forEach(playerTurn -> {
            playerTurn.setHand(getNCardFromReserve(2, turn.getReserve()), Hand.createHand(Visibility.COMPLETE, HandType.OWN));
        });
        turn.setDealerHand(getNCardFromReserve(2, turn.getReserve()), Hand.createHand(Visibility.PARTIAL, HandType.DEALER));
    }

    private List<Card> getNCardFromReserve(int n, List<Card> reserve) {
        List<Card> drawnCards = new ArrayList<>(reserve.subList(0, n));
        reserve.subList(0, n).clear(); // remove them from deck
        return drawnCards;
    }

    private void invitePlayersToPlayHand(Turn turn) {
        Deque<PlayerTurn> turnsToPlay = new ArrayDeque<>(turn.getPlayerTurns());
        //Remake it with Deque//
        while (!turnsToPlay.isEmpty()) {
            PlayerTurn playerTurn = turnsToPlay.pop();
            registerPlay(turn,playerTurn,turnsToPlay);
        }

    }

    private void registerPlay(Turn turn, PlayerTurn playerTurn,Deque<PlayerTurn> turnsToPlay) {
        Action playerAction;
        Player player = playerTurn.getPlayer();
        do {
            playerAction = player.pickAction();
            validateAction(playerAction, playerTurn);
        } while (!playerAction.execute(, turn.getReserve(), , playerTurn, this::getNCardFromReserve));
    }

    private void validateAction(Action playerAction, PlayerTurn playerTurn) throws IllegalActionException {


    }

    public Game getGame() {
        return game;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public List<Card> getFullDeck() {
        return fullDeck;
    }

    public void setFullDeck(List<Card> fullDeck) {
        this.fullDeck = fullDeck;
    }

}
