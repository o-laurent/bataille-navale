package ensta.AbstractShip;

public class Submarine extends AbstractShip {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 8L;

    /** Default constructor */
    public Submarine() {
        super("Submarine", 'S', 3, null);
    }

    /**
     * Valued constructor
     * 
     * @param orient the orientation of the ship
     */
    public Submarine(Orientation orient) {
        super("Submarine", 'S', 3, orient);
    }
}
