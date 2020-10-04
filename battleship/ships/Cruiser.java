package battleship.ships;

public class Cruiser implements Ship {

    private static final String NAME = "Cruiser";
    private static final int SIZE = 3;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
