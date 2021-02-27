package ensta.AbstractShip;

public class Destroyer extends AbstractShip {
    private static final long serialVersionUID = 7L;

    public Destroyer() {
        super("Destroyer", 'D', 2, null);
    }

    public Destroyer(Orientation orient) {
        super("Destroyer", 'D', 2, orient);
    }
}
