package ensta.AbstractShip;

public class Submarine extends AbstractShip{
    public Submarine() {
        super("Submarine", 'S', 3, null);
    }

    public Submarine(Orientation orient) {
        super("Submarine", 'S', 3, orient);
    }
}
