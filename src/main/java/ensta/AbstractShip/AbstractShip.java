package ensta.AbstractShip;

import java.io.Serializable;

public class AbstractShip implements Serializable {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 4L;

    private Character label;
    private String fullname;
    private int size;
    private Orientation orientation;

    private int strikeCount;

    /**
     * Method to get the label of the ship (GETTER)
     * 
     * @return the label of the ship
     */
    public Character getLabel() {
        return this.label;
    }

    /**
     * Method to get the full name of the ship (GETTER)
     * 
     * @return the full name of the ship
     */
    public String getFullname() {
        return this.fullname;
    }

    /**
     * Method to get the size of the ship (GETTER)
     * 
     * @return the size of the ship
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Method to get the orientation of the ship (GETTER)
     * 
     * @return the orientation of the ship
     */
    public Orientation getOrientation() {
        return this.orientation;
    }

    /**
     * Method to get the orientation of the ship (GETTER)
     * 
     * @return the number of strikes on the ship
     */
    public int howStruck() {
        return this.strikeCount;
    }

    /**
     * Method to get the orientation of the ship (GETTER)
     * 
     * @return true if the ship is sunk, false if not
     */
    public boolean isSunk() {
        if (this.size == this.strikeCount) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to get the orientation of the ship (SETTER)
     * 
     * @param the orientation
     */
    public void setOrientation(Orientation orient) {
        this.orientation = orient;
    }

    /**
     * Method to increase the number of strikes on the ship (SETTER)
     */
    public void addStrike() {
        this.strikeCount++;
    }

    /**
     * Valued constructor
     * 
     * @param fullname name of the ship
     * @param label    short name of the ship
     * @param size     size of the ship
     * @param orient   orientation of the ship
     */
    public AbstractShip(String fullname, Character label, int size, Orientation orient) {
        this.fullname = fullname;
        this.label = label;
        this.size = size;
        this.orientation = orient;
        this.strikeCount = 0;
    }
}
