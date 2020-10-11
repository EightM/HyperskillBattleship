package battleship;

import battleship.ships.Ship;

public class Game {
    private final Board board;

    public Game() {
        this.board = new Board(10);
    }

    public void placeShipOnBoard(Ship ship) {
        board.placeShip(ship);
        board.printField();
    }

    public void printBoard() {
        board.printField();
    }

    public void start() {
        System.out.println("The game starts");
        board.printBattleField();
        System.out.println("Take a shot!");
        while (!board.allShipsDestroyed()) {
            board.takeAShot();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}
