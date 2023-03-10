package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demo.Symbol.PAPER;
import static com.example.demo.Symbol.ROCK;
import static com.example.demo.Symbol.SCISSORS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameApplicationTests {
    Player user = Player.builder()
            .playerName("USER")
            .build();
    Player computer = Player.builder()
            .playerName("COMPUTER")
            .build();
    private Game game = Game.builder()
            .status(Status.STARTED)
            .noOfMatches(0)
            .tie(0)
            .options(new Symbol[]{ROCK, PAPER, SCISSORS})
            .players(List.of(user, computer))
            .build();

    @Test
    public void testIsValidChoice() {
        Assertions.assertTrue(game.isValidChoice("rock"));
        Assertions.assertTrue(game.isValidChoice("paper"));
        Assertions.assertTrue(game.isValidChoice("scissors"));
        Assertions.assertFalse(game.isValidChoice("foo"));
        Assertions.assertFalse(game.isValidChoice(""));
    }

    @Test
    void when_game_tie() {
        assertThat(game.getResult("rock", "rock")).isEqualTo(Result.TIE);
        assertThat(game.getResult("paper", "paper")).isEqualTo(Result.TIE);
        assertThat(game.getResult("scissors", "scissors")).isEqualTo(Result.TIE);
    }

    @Test
    void when_Player_win() {
        assertThat(game.getResult("rock", "scissors")).isEqualTo(Result.WIN);
        assertThat(game.getResult("paper", "rock")).isEqualTo(Result.WIN);
        assertThat(game.getResult("scissors", "paper")).isEqualTo(Result.WIN);
    }

    @Test
    void when_Player_loose() {
        assertThat(game.getResult("scissors", "rock")).isEqualTo(Result.LOOSE);
        assertThat(game.getResult("rock", "paper")).isEqualTo(Result.LOOSE);
        assertThat(game.getResult("paper", "scissors")).isEqualTo(Result.LOOSE);
    }
}
