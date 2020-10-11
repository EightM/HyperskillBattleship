package battleship;

import java.util.Scanner;

public class Game {
    private final Player firstPlayer = new Player(new Board(10));
    private final Player secondPlayer = new Player(new Board(10));
    private static final String PASS_TURN_MESSAGE = "Press Enter and pass the move to another player";

    public void placeShipsOnBoard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Player 1, place your ships on the game field");
        firstPlayer.placeShips();

        System.out.println(PASS_TURN_MESSAGE);
        scanner.nextLine();

        System.out.println("Player 2, place your ships on the game field");
        secondPlayer.placeShips();

        System.out.println(PASS_TURN_MESSAGE);
        scanner.nextLine();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Player currentPlayer = firstPlayer;
        Player otherPlayer = secondPlayer;
        boolean isGameFinished = firstPlayer.allShipsDestroyed() && secondPlayer.allShipsDestroyed();
        while (!isGameFinished) {

            printBeginTurnMessage(currentPlayer);
            currentPlayer.takeATurn(otherPlayer);

            isGameFinished = firstPlayer.allShipsDestroyed() || secondPlayer.allShipsDestroyed();
            if (isGameFinished) {
                break;
            }

            currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
            otherPlayer = otherPlayer == firstPlayer ? secondPlayer : firstPlayer;
            System.out.println(PASS_TURN_MESSAGE);
            scanner.nextLine();

        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private void printBeginTurnMessage(Player currentPlayer) {
        if (currentPlayer == firstPlayer) {
            System.out.println("Player 1, it's your turn:");
        } else {
            System.out.println("Player 2, it's your turn:");
        }
    }
}
