package ensta;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.Orientation;
import ensta.AbstractShip.ShipState;

public class Board implements IBoard {
    private String name;
    private int gridSize;
    private ShipState[][] shipGrid;
    private Boolean[][] hitGrid;

    public void putShip(AbstractShip ship, int x, int y) throws Exception {
        int gridSize = getSize();
        if (hasShip(x, y)) {
            throw new Exception("There is already a ship at these coordinates.");
        }
        if (x < 1 || y < 1 || x > gridSize + 1 || y > gridSize + 1) {
            throw new Exception("The coordinates are wrong.");
        }
        Orientation orient = ship.getOrientation();
        int size = ship.getSize();
        switch (orient) {
            case EAST:
                if ((y - 1) + (size - 1) >= getSize()) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[x - 1][y + i - 1] = new ShipState(ship);
                }
                break;
            case WEST:
                if ((y - 1) - (size - 1) < 0) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[x - 1][y - i - 1] = new ShipState(ship);
                }
                break;
            case SOUTH:
                if ((x - 1) + (size - 1) >= getSize()) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[x + i - 1][y - 1] = new ShipState(ship);
                }
                break;

            case NORTH:
                if ((x - 1) - (size - 1) < 0) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[x - i - 1][y - 1] = new ShipState(ship);
                }
                break;
        }

    }

    public boolean hasShip(int x, int y) throws Exception {
        int size = getSize();
        if (x < 1 || y < 1 || x > size || y > size) {
            throw new Exception("The coordinates are wrong.");
        }
        return !(shipGrid[x - 1][y - 1].getShip() == null || shipGrid[x - 1][y - 1].isSunk());
    }

    public void setHit(boolean hit, int x, int y) throws Exception {
        int size = getSize();
        if (x < 1 || y < 1 || x > size || y > size) {
            throw new Exception("The coordinates are wrong.");
        }
        hitGrid[x - 1][y - 1] = true;
    }

    public Boolean getHit(int x, int y) throws Exception {
        int size = getSize();
        if (x < 1 || y < 1 || x > size || y > size) {
            throw new Exception("The coordinates are wrong.");
        }
        return hitGrid[x - 1][y - 1];
    }

    /**
     * Get the name of the board
     * 
     * @return fullname
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the size of the board
     * 
     * @return size
     */
    public int getSize() {
        return this.gridSize;
    }

    /**
     * Get the whole shipGrid
     * 
     * @return the ship grid
     */
    public ShipState[][] getShipGrid() {
        return this.shipGrid;
    }

    /**
     * Get the whole hitGrid
     * 
     * @return the hit grid
     */
    public Boolean[][] getHitGrid() {
        return this.hitGrid;
    }

    public Hit sendHit(int x, int y) {
        int value = 0;
        ShipState shipState = getShipGrid()[x-1][y-1];
        try {
            shipState.addStrike();
        } catch (Exception exception) {
            value = 0; // Error
        }
        if (shipState.getShip() == null) {
            value = -1;
        } else if (!shipState.isSunk()) {
            value = -2;
        } else {
            switch (shipState.getShip().getFullname()) {
                case "Destroyer":
                    value = 2;
                    break;
                case "Submarine":
                    value = 3;
                    break;
                case "Battleship":
                    value = 4;
                    break;
                case "Carrier":
                    value = 5;
                    break;
            }
        }

        return Hit.fromInt(value);
    }

    /**
     * Print the two boards
     */
    public void print() {
        System.out.println("Ship Grid");

        System.out.print("   "); // lineNb space
        for (Character c = 'A'; c < 'A' + getSize() - 1; c++) {
            System.out.print(c + " ");
        }
        System.out.println((char) ('A' + getSize() - 1));
        int lineNb = 1;
        for (ShipState[] x : getShipGrid()) {
            if (lineNb < 10)
                System.out.print(lineNb++ + "  ");
            else
                System.out.print(lineNb++ + " ");
            for (ShipState y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        System.out.println("Hit Grid");

        System.out.print("   "); // lineNb space
        for (Character c = 'A'; c < 'A' + getSize() - 1; c++) {
            System.out.print(c + " ");
        }
        System.out.println((char) ('A' + getSize() - 1));
        lineNb = 1;
        for (Boolean[] x : getHitGrid()) {
            if (lineNb < 10)
                System.out.print(lineNb++ + "  ");
            else
                System.out.print(lineNb++ + " ");
            for (Boolean y : x) {
                if (y == null) {
                    System.out.print(". ");
                } else if (y == false) {
                    System.out.print("X ");
                } else {
                    System.out.print(ColorUtil.colorize("X ", ColorUtil.Color.RED));
                }

            }
            System.out.println();
        }
    }

    /**
     * Valued contructor
     * 
     * @param name
     * @param gridSize
     */
    public Board(String name, int gridSize) {
        this.name = name;
        this.gridSize = gridSize;

        this.shipGrid = new ShipState[gridSize][gridSize];
        for (int x = 0; x < gridSize; ++x) {
            for (int y = 0; y < gridSize; ++y) {
                this.shipGrid[x][y] = new ShipState();
            }
        }
        this.hitGrid = new Boolean[gridSize][gridSize];
        for (int x = 0; x < gridSize; ++x) {
            for (int y = 0; y < gridSize; ++y) {
                this.hitGrid[x][y] = null;
            }
        }
    }

    /**
     * Valued contructor
     * 
     * @param name
     */
    public Board(String name) {
        this.name = name;
        this.gridSize = 10;
        this.shipGrid = new ShipState[10][10];
        for (int x = 0; x < 10; ++x) {
            for (int y = 0; y < 10; ++y) {
                this.shipGrid[x][y] = new ShipState();
            }
        }
        this.hitGrid = new Boolean[10][10];
        for (int x = 0; x < 10; ++x) {
            for (int y = 0; y < 10; ++y) {
                this.hitGrid[x][y] = null;
            }
        }
        // System.out.print(this.hitGrid[0][0]);
    }
}
