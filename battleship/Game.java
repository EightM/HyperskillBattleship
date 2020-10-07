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
        board.takeAShot();
    }
}
