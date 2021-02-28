package ensta.AbstractShip;

public class Destroyer extends AbstractShip {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 7L;

    /** Default constructor */
    public Destroyer() {
        super("Destroyer", 'D', 2, null);
    }

    /**
     * Valued constructor
     * 
     * @param orient the orientation of the ship
     */
    public Destroyer(Orientation orient) {
        super("Destroyer", 'D', 2, orient);
    }
}
