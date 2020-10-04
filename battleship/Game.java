package battleship;

import battleship.ships.Ship;

public class Game {
    private final Board board;

    public Game() {
        this.board = new Board(10);
    }

    public void placeShipOnBoard(Ship ship) {
        board.placeShip(ship);
    }

    public void printBoard() {
        board.printField();
    }
}
