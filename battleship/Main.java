package battleship;

import battleship.ships.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        Ship aircraft = new AircraftCarrier();
        Ship battleship = new Battleship();
        Ship submarine = new Submarine();
        Ship cruiser = new Cruiser();
        Ship destroyer = new Destroyer();

        game.placeShipOnBoard(aircraft);
        game.printBoard();
        game.placeShipOnBoard(battleship);
        game.printBoard();
        game.placeShipOnBoard(submarine);
        game.printBoard();
        game.placeShipOnBoard(cruiser);
        game.printBoard();
        game.placeShipOnBoard(destroyer);
        game.printBoard();
    }
}
