package battleship.ships;

public class Submarine implements Ship {

    private static final String NAME = "Submarine";
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
