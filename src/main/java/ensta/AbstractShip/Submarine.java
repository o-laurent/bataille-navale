package ensta.AbstractShip;

public class Submarine extends AbstractShip{
    private static final long serialVersionUID = 8L;

    public Submarine() {
        super("Submarine", 'S', 3, null);
    }

    public Submarine(Orientation orient) {
        super("Submarine", 'S', 3, orient);
    }
}
