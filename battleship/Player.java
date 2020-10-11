package battleship;

import battleship.ships.*;

public class Player {
    private final Board board;

    public Player(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void placeShips() {
        board.printField();
        Ship aircraft = new AircraftCarrier();
        Ship battleship = new Battleship();
        Ship submarine = new Submarine();
        Ship cruiser = new Cruiser();
        Ship destroyer = new Destroyer();
        board.placeShip(aircraft);
        board.placeShip(battleship);
        board.placeShip(submarine);
        board.placeShip(cruiser);
        board.placeShip(destroyer);
    }

    public boolean allShipsDestroyed() {
        return board.allShipsDestroyed();
    }

    public void takeATurn(Player otherPlayer) {
        board.printAllFields();
        board.takeAShot(otherPlayer.getBoard());
    }
}
