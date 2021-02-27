package ensta.AbstractShip;

import ensta.ColorUtil;

public class ShipState {
    private AbstractShip ship;
    private boolean struck;

    public void addStrike() throws Exception {
        if (struck) {
            throw new Exception("The ship has already been struck");
        } else {
            this.struck = true;
            getShip().addStrike();
        }
    }

    public boolean isStruck() {
        return this.struck;
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

    public String toString() {
        if (this.ship == null) {
            if (this.struck)
                return ColorUtil.colorize(".", ColorUtil.Color.RED);
            else
                return ".";

        } else if (!this.struck) {
            return ship.getLabel().toString();
        } else
            return ColorUtil.colorize(ship.getLabel(), ColorUtil.Color.RED);
    }
}
