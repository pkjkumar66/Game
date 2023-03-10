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

    // This method will be used to initialize the member variables
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

    // This method will be used to start the game
    public void start() {
        status = Status.STARTED;
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------GAME STARTED-----------");

        while (true) {
            String userMove = userMove(scanner);

            // if user wishes to quit the game then he/she can choose the quit option and the loop will terminate
            if (userMove.equals("quit") || userMove.equals("exit")) {
                System.out.println();
                break;
            }

            String computerMove = computerChoice();
            noOfMatches++;
            getResult(userMove, computerMove);
            System.out.println();
        }

        status = Status.FINISHED;
        scanner.close();
        System.out.println("----------GAME END-----------");
        System.out.println();
    }

    // This method will give the result on the basis of choices made by user and computer
    // It contains the rock paper scissors game rule and decided the result using it.
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

    // This method is implemented for user which he/she chooses from one of the available options
    private String userMove(Scanner scanner) {
        String playerChoice = "";

        while (true) {
            System.out.println("Enter your choice (rock/paper/scissors) or quit to exit:  ");
            playerChoice = scanner.nextLine();

            if (playerChoice.equals("quit") || playerChoice.equals("exit")) {
                break;
            }

            if (isValidChoice(playerChoice)) {
                break;
            }

            System.out.println(playerChoice + ", Invalid choice, please try again.");
        }

        return playerChoice;
    }

    // This method will check whether user has given correct/incorrect input
    public boolean isValidChoice(String playerChoice) {
        if (playerChoice.equals("rock") || playerChoice.equals("paper") || playerChoice.equals("scissors")) {
            return true;
        }
        return false;
    }

    // This method is implemented for computer choice which randomly chooses from one of the available options.
    private String computerChoice() {
        Random random = new Random();
        int computerChoice = random.nextInt(options.length);
        System.out.println("Computer chooses: " + options[computerChoice]);
        return options[computerChoice].toString().toLowerCase();
    }

    // This method will print the score, which shows how many matches were played in one session
    // and whether the user or computer won, lost, or tied
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
