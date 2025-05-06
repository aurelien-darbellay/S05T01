package aDarbellay.s05.t1.model.player;

import org.springframework.stereotype.Service;

@Service
public class PlayerFactory {
    public Player createNewPlayer(){
        return Math.random()<0.5 ? new CautiousPlayer() : new RandomPlayer();
    }
}
