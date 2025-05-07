package aDarbellay.s05.t1.model.player;

import org.springframework.stereotype.Service;

@Service
public class PlayerFactory {
    public Player createNewPlayer(){
        return Math.random()<0.5 ? new CautiousPlayer() : new RandomPlayer();
    }

    public Player createNewPlayer(String firstName, String lastName ){
        RealPlayer realPlayer = new RealPlayer();
        realPlayer.setFirstname(firstName);
        realPlayer.setLastname(lastName);
        return realPlayer;
    }
}
