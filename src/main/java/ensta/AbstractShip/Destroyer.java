package ensta.AbstractShip;

public class Destroyer extends AbstractShip {

    public Destroyer() {
        super("Destroyer", 'D', 2, null);
    }

    public Destroyer(Orientation orient) {
        super("Destroyer", 'D', 2, orient);
    }
}
