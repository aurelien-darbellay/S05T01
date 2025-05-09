package aDarbellay.s05.t1.model.player;

import org.springframework.stereotype.Service;

@Service
public class PlayerFactory {
    public Player createNewPlayer() {
        return Math.random() < 0.5 ? new CautiousPlayer() : new RandomPlayer();
    }

    public RealPlayer createNewPlayer(String firstName, String lastName, String userName) {
        RealPlayer realPlayer = new RealPlayer();
        realPlayer.setFirstName(firstName);
        realPlayer.setLastName(lastName);
        realPlayer.setUserName(userName);
        return realPlayer;
    }
}
