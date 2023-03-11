package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameApplication.class, args);

        Game rockPaperScissors = Game.builder().build();
        rockPaperScissors.initialize();
        rockPaperScissors.start();

        System.out.println("Every match winner info: ");
        System.out.println("[If information about the winner of a match is not printed, it means that the match ended in a tie.]");
        rockPaperScissors.printScoreCardPerMatch();
        System.out.println("Game ScoreCard: ");
        rockPaperScissors.printScoreCard();
    }

}
