package battleship;

import battleship.ships.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.placeShipsOnBoard();
        game.start();

    }
}
