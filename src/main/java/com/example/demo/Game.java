package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static com.example.demo.Symbol.PAPER;
import static com.example.demo.Symbol.ROCK;
import static com.example.demo.Symbol.SCISSORS;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private List<Player> players;
    private Status status;
    private Symbol[] options;
    private int noOfMatches;
    private int tie;
    private static final String USER = "User";
    private static final String COMPUTER = "Computer";

    public void initialize() {
        noOfMatches = 0;
        tie = 0;
        status = Status.NOT_STARTED;
        options = new Symbol[]{ROCK, PAPER, SCISSORS};
        addPlayers();
    }

    private void addPlayers() {
        if (status == Status.NOT_STARTED) {
            players = new ArrayList<>();
            Player user = Player.builder()
                    .playerName(USER)
                    .build();
            Player computer = Player.builder()
                    .playerName(COMPUTER)
                    .build();

            players.add(user);
            players.add(computer);
        }
    }

    public void start() {
        status = Status.STARTED;
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------GAME STARTED-----------");

        while (true) {
            String userMove = userMove(scanner);
            if (userMove.equals("QUIT")) {
                System.out.println();
                break;
            }

            String computerMove = computerMove();
            noOfMatches++;
            getResult(userMove, computerMove);
            System.out.println();
        }

        status = Status.FINISHED;
        scanner.close();
        System.out.println("----------GAME END-----------");
        System.out.println();
    }

    public Result getResult(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            tie++;
            return Result.TIE;

        } else if (playerChoice.equals("rock") && computerChoice.equals("scissors")
                || playerChoice.equals("paper") && computerChoice.equals("rock")
                || playerChoice.equals("scissors") && computerChoice.equals("paper")) {

            Player user = players.stream().filter(p -> p.getPlayerName().equals(USER)).findFirst().orElse(null);
            if (Objects.nonNull(user)) {
                user.updateScore();
            }

            return Result.WIN;
        } else {
            Player computer = players.stream().filter(p -> p.getPlayerName().equals(COMPUTER)).findFirst().orElse(null);

            if (Objects.nonNull(computer)) {
                computer.updateScore();
            }
            return Result.LOOSE;
        }
    }

    private String userMove(Scanner scanner) {
        String playerChoice = "";

        while (true) {
            System.out.println("Enter your choice (rock/paper/scissors) or quit to exit:  ");
            playerChoice = scanner.nextLine();

            if (playerChoice.equals("quit")) {
                break;
            }

            if (isValidChoice(playerChoice)) {
                break;
            }

            System.out.println(playerChoice + ", Invalid choice, please try again.");
        }

        return playerChoice.toUpperCase();
    }

    public boolean isValidChoice(String playerChoice) {
        if (playerChoice.equals("rock") || playerChoice.equals("paper") || playerChoice.equals("scissors")) {
            return true;
        }
        return false;
    }

    private String computerMove() {
        Random random = new Random();
        int computerChoice = random.nextInt(options.length);
        System.out.println("Computer chooses: " + options[computerChoice]);
        return options[computerChoice].toString();
    }

    public void printScoreCard() {
        System.out.format("%-10s %6s %6s %6s %6s", "Players", "Matches", "Win", "Loose", "Tie\n");

        for (Player player : players) {
            System.out.format("%-10s %6s %6s %6s %6s",
                    player.getPlayerName(),
                    getNoOfMatches(),
                    player.getScore(),
                    (getNoOfMatches() - getTie() - player.getScore()),
                    getTie()
            );
            System.out.println();
        }
    }
}
