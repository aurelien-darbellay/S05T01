package aDarbellay.s05.t1.model;

import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.model.actions.Action;
import aDarbellay.s05.t1.model.hands.Hand;
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
        revealDealerHand(newTurn);
        calculateResults(newTurn);
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
            playerTurn.setHand(Hand.createPlayerHand(getNCardFromReserve(2, turn.getReserve())));
        });
        turn.setDealerHand(Hand.createDealerHand(getNCardFromReserve(2, turn.getReserve())));
    }

    private List<Card> getNCardFromReserve(int n, List<Card> reserve) {
        List<Card> drawnCards = new ArrayList<>(reserve.subList(0, n));
        reserve.subList(0, n).clear(); // remove them from deck
        return drawnCards;
    }

    private void invitePlayersToPlayHand(Turn turn) {
        Deque<PlayerTurn> playsToRegister = new ArrayDeque<>(turn.getPlayerTurns());

        while (!playsToRegister.isEmpty()) {
            PlayerTurn playerTurn = playsToRegister.pop();
            registerPlay(turn, playerTurn, playsToRegister);
        }

    }

    private void registerPlay(Turn turn, PlayerTurn playerTurn, Deque<PlayerTurn> turnsToPlay) {
        Action playerAction;
        Player player = playerTurn.getPlayer();
        do {
            playerAction = player.pickAction();
            validateAction(playerAction, playerTurn);
        } while (!playerAction.execute(turn, turnsToPlay, playerTurn, this::getNCardFromReserve) && playerTurn.getHand().getHandValue() < 21);
    }

    private void validateAction(Action playerAction, PlayerTurn playerTurn) throws IllegalActionException {


    }

    private void revealDealerHand(Turn turn) {
        Hand dealerHand = turn.getDealerHand();
        dealerHand.setVisibility(Visibility.COMPLETE);
        while (dealerHand.getHandValue() < 17) {
            Card newCard = getNCardFromReserve(1, turn.getReserve()).getFirst();
            dealerHand.add(newCard);
        }
    }

    private void calculateResults(Turn turn) {
        int dealerPoints = turn.getDealerHand().getHandValue();
        turn.getPlayerTurns().forEach(playerTurn -> {
            int playerPoints = playerTurn.getHand().getHandValue();
            if (playerPoints > 21) playerTurn.setResult(PlayerTurn.ResultType.LOSS);
            else if (dealerPoints > 21) playerTurn.setResult(PlayerTurn.ResultType.WIN);
            else {
                setResultIfNoneBust(playerPoints, dealerPoints, playerTurn, turn);
            }
        });
    }

    private void setResultIfNoneBust(int playerPoints, int dealerPoints, PlayerTurn playerTurn, Turn turn) {
        switch (Integer.signum(playerPoints - dealerPoints)) {
            case 1:
                playerTurn.setResult(PlayerTurn.ResultType.WIN);
                break;
            case -1:
                playerTurn.setResult(PlayerTurn.ResultType.LOSS);
                break;
            case 0:
                setResultByHandSize(playerTurn, turn.getDealerHand());
                break;
        }
    }

    private void setResultByHandSize(PlayerTurn playerTurn, Hand dealerHand) {
        if (playerTurn.getHand().size() == 2) {
            if (dealerHand.size() == 2) playerTurn.setResult(PlayerTurn.ResultType.PUSH);
            else playerTurn.setResult(PlayerTurn.ResultType.WIN);
        } else if (dealerHand.size() == 2) playerTurn.setResult(PlayerTurn.ResultType.LOSS);
        else playerTurn.setResult(PlayerTurn.ResultType.PUSH);
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
