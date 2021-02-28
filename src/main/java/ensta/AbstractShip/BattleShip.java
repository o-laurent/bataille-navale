package ensta.AbstractShip;

public class BattleShip extends AbstractShip {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 5L;

    /** Default constructor */
    public BattleShip() {
        super("Battleship", 'B', 4, null);
    }

    /**
     * Valued constructor
     * 
     * @param orient the orientation of the ship
     */
    public BattleShip(Orientation orient) {
        super("Battleship", 'B', 4, orient);
    }
}
