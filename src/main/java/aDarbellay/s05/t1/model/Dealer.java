package aDarbellay.s05.t1.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dealer {

    private final Game game;
    private boolean gameOn;
    private List<Card> fullDeck;


    public Turn runTurn(){
        setGameOn(true);
        List<Player> players = game.getPlayers();
        Turn newTurn = initializeNewTurn(game);
        takeBets(newTurn);
        distributeCard(newTurn);
        game.setNumTurnsPlayed(newTurn.getId());
        setGameOn(false);
        return newTurn;
    }

    private Turn initializeNewTurn(Game game){
        Turn newTurn = new Turn(game.getNumTurnsPlayed()+1);
        List<PlayerTurn> playerTurns = createPlayerTurns(newTurn,game.getPlayers());
        newTurn.setPlayerTurns(playerTurns);
        return newTurn;
    }
    private List<PlayerTurn> createPlayerTurns(Turn turn,List<Player> players){
        return players.stream().map(player -> new PlayerTurn(turn.getId(),player)).toList();
    }
    private void takeBets(Turn turn){
        turn.getPlayerTurns().forEach(playerTurn -> playerTurn.getPlayer().placeBet(playerTurn));
    }

    private void distributeCard(Turn turn){
        Collections.shuffle(fullDeck);
        turn.setReserve(fullDeck);
        turn.getPlayerTurns().forEach(playerTurn -> playerTurn.setHand(getNCardFromReserve(2,turn.getReserve())));
        turn.setDealerHand();
    }

    private List<Card> getNCardFromReserve(int n, List<Card> reserve){
        List<Card> drawnCards = new ArrayList<>(reserve.subList(0, n));
        reserve.subList(0, n).clear(); // remove them from deck
        return drawnCards;
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

    public Dealer(Game game, List<Card> fullDeck){
        this.game = game;
        this.gameOn = false;
        this.fullDeck = fullDeck;
    }




}
