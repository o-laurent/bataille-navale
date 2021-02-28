package ensta.AbstractShip;

import java.io.Serializable;

import ensta.ColorUtil;

public class ShipState implements Serializable {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 7L;

    /** Other Attribute */
    private AbstractShip ship;
    private boolean struck;

    /**
     * Public method to add a strike to the ShipState
     * 
     * @throws Exception if the coordinates are wrong
     */
    public void addStrike() throws Exception {

        if (struck) {
            throw new Exception("The ship has already been struck");
        } else {
            this.struck = true;
            if (this.getShip() != null)
                this.getShip().addStrike();
        }
    }

    /**
     * Public method which returns if these coordinates have been struck (GETTER)
     * 
     * @return true if these coordinates have been struck
     */
    public boolean isStruck() {
        return this.struck;
    }

    /**
     * Public method which returns if the ship is struck (GETTER)
     * 
     * @return true if the ship is struck
     */
    public boolean isSunk() {
        return this.ship.isSunk();
    }

    /**
     * Public method which returns the ship (GETTER)
     * 
     * @return the ship corresponding to the ShipState
     */
    public AbstractShip getShip() {
        return this.ship;
    }

    /**
     * Public method to set the corresponding ship (SETTER)
     * 
     * @param argShip
     */
    public void setShip(AbstractShip argShip) {
        this.ship = argShip;
    }

    /**
     * Default constructor
     */
    public ShipState() {
        this.ship = null;
        this.struck = false;
    }

    /**
     * Valued constructor
     * 
     * @param argShip the corresponding ship
     */
    public ShipState(AbstractShip argShip) {
        this.ship = argShip;
        this.struck = false;
    }

    /**
     * Stringify the ShipState object
     * 
     * @return the string
     */
    public String toString() {
        if (this.ship == null) {
            if (this.struck)
                return ColorUtil.colorize(".", ColorUtil.Color.RED);
            else
                return ".";

        } else if (!this.struck) {
            return this.ship.getLabel().toString();
        } else
            return ColorUtil.colorize(ship.getLabel(), ColorUtil.Color.RED);
    }
}
