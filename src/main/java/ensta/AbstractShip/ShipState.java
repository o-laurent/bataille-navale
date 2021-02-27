package ensta.AbstractShip;

import ensta.ColorUtil;

public class ShipState {
    private AbstractShip ship;
    private boolean struck;

    public void addStrike() throws Exception {
        if (struck) {
            throw new Exception("The ship has already been struck");
        }
        else {
            this.struck = true;
        }
    }
    public boolean isStruck(){
        return this.struck;
    }

    public String toString() {
        return ColorUtil.colorize(ship.getFullname(), ColorUtil.Color.RED);
    }
 
    public boolean isSunk() {
        return this.ship.isSunk();
    }

    public AbstractShip getShip() {
        return this.ship;
    }

    public void setShip(AbstractShip argShip) {
        this.ship = argShip;
    }

    public ShipState() {
        this.ship = null;
        this.struck = false;
    }

    public ShipState(AbstractShip argShip) {
        this.ship = argShip;
        this.struck = false;
    }
}
