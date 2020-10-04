package battleship.ships;

public class Destroyer implements Ship {

    private static final String NAME = "Destroyer";
    private static final int SIZE = 2;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
