package aDarbellay.s05.t1;

import aDarbellay.s05.t1.controller.GameController;
import aDarbellay.s05.t1.controller.RootController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S05T01Application {

    public static void main(String[] args) {
        SpringApplication.run(S05T01Application.class, args);
    }

    @Autowired
    GameController gameController;

    @Autowired
    RootController rootController;

}
