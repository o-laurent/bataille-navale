package ensta.AbstractShip;

public class Carrier extends AbstractShip {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 6L;

    /** Default constructor */
    public Carrier() {
        super("Carrier", 'C', 5, null);
    }

    /**
     * Valued constructor
     * 
     * @param orient the orientation of the ship
     */
    public Carrier(Orientation orient) {
        super("Carrier", 'C', 5, orient);
    }
}
