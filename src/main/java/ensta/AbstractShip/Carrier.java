package ensta.AbstractShip;

public class Carrier extends AbstractShip{
    public Carrier() {
        super("Carrier", 'C', 5, null);
    }

    public Carrier(Orientation orient) {
        super("Carrier", 'C', 5, orient);
    }
}
