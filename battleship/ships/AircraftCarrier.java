package battleship.ships;

public class AircraftCarrier implements Ship {
    private static final String NAME = "Aircraft Carrier";
    private static final int SIZE = 5;

    public String getName() {
        return NAME;
    }

    public int getSize() {
        return SIZE;
    }

}
