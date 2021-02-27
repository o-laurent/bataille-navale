package ensta.AbstractShip;

public class Carrier extends AbstractShip{
    private static final long serialVersionUID = 6L;
    
    public Carrier() {
        super("Carrier", 'C', 5, null);
    }

    public Carrier(Orientation orient) {
        super("Carrier", 'C', 5, orient);
    }
}
