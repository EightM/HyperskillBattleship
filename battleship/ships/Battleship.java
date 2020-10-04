package battleship.ships;

public class Battleship implements Ship {

    private static final String NAME = "Battleship";
    private static final int SIZE = 4;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
