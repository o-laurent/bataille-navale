package ensta;

import java.io.Serializable;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.Orientation;
import ensta.AbstractShip.ShipState;

public class Board implements IBoard, Serializable {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 3L;

    /**
     * The name of the board/player.
     */
    private String name;

    /**
     * The size of the Boards.
     */
    private int gridSize;

    /**
     * The board representing the ships.
     */
    private ShipState[][] shipGrid;

    /**
     * The board memorizing the hits.
     */
    private Boolean[][] hitGrid;

    /**
     * Method add a ship on the ShipState board
     * 
     * @param ship the ship that is to be placed
     * @param x    The USER y-axis coordinate
     * @param y    The USER x-axis coordinate
     * @throws Exception if x or y are not good USER coordinates or if the ship
     *                   cannot be placed (already a ship at these coordinates)
     */
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
                    if (hasShip(x, y + i)) {
                        throw new Exception("There is already a ship at these coordinates.");
                    }
                    this.shipGrid[x - 1][y + i - 1] = new ShipState(ship);
                }
                break;
            case WEST:
                if ((y - 1) - (size - 1) < 0) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    if (hasShip(x, y - i)) {
                        throw new Exception("There is already a ship at these coordinates.");
                    }
                    this.shipGrid[x - 1][y - i - 1] = new ShipState(ship);
                }
                break;
            case SOUTH:
                if ((x - 1) + (size - 1) >= getSize()) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    if (hasShip(x + i, y)) {
                        throw new Exception("There is already a ship at these coordinates.");
                    }
                    this.shipGrid[x + i - 1][y - 1] = new ShipState(ship);
                }
                break;

            case NORTH:
                if ((x - 1) - (size - 1) < 0) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    if (hasShip(x - i, y)) {
                        throw new Exception("There is already a ship at these coordinates.");
                    }
                    this.shipGrid[x - i - 1][y - 1] = new ShipState(ship);
                }
                break;
        }

    }

    /**
     * Method to know if there is a ship on the specified coordinates
     * 
     * @param x The USER y-axis coordinate
     * @param y The USER x-axis coordinate
     * @return true if there is a ship, false if not
     * @throws Exception if x or y are not good USER coordinates
     */
    public boolean hasShip(int x, int y) throws Exception {
        int size = this.getSize();
        if (x < 1 || y < 1 || x > size || y > size) {
            throw new Exception("The coordinates are wrong.");
        }
        return !(this.shipGrid[x - 1][y - 1].getShip() == null || this.shipGrid[x - 1][y - 1].isSunk());
    }

    /**
     * Method to set a hit defined by the coordinates x and y
     * 
     * @param x The COMPUTER y-axis coordinate
     * @param y The COMPUTER x-axis coordinate
     * @throws Exception if x or y are not good COMPUTER coordinates
     */
    public void setHit(boolean hit, int x, int y) throws Exception {
        int size = this.getSize();
        if (x < 0 || y < 0 || x >= size || y >= size) {
            throw new Exception("The coordinates are wrong.");
        }
        this.hitGrid[x][y] = hit;
    }

    /**
     * Method to get a hit defined by the coordinates x and y
     * 
     * @param x The COMPUTER y-axis coordinate
     * @param y The COMPUTER x-axis coordinate
     * @return null, true or false depending of the hit
     * @throws Exception if x or y are not good COMPUTER coordinates
     */
    public Boolean getHit(int x, int y) throws Exception {
        int size = getSize();
        if (x < 0 || y < 0 || x >= size || y >= size) {
            throw new Exception("The coordinates are wrong.");
        }
        return this.hitGrid[x][y];
    }

    /**
     * Public method to get the name of the board/player (GETTER)
     * 
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Public method to get the size of the board (GETTER)
     * 
     * @return the size of the board
     */
    public int getSize() {
        return this.gridSize;
    }

    /**
     * Method to get the whole shipGrid (GETTER)
     * 
     * @return the ship grid made of ShipStates
     */
    public ShipState[][] getShipGrid() {
        return this.shipGrid;
    }

    /**
     * Public method to get the whole hitGrid
     * 
     * @return the hit grid (of null -not struck-, true -struck with a ship- or
     *         false -struck without ship-)
     */
    public Boolean[][] getHitGrid() {
        return this.hitGrid;
    }

    /**
     * Method to compute a hit defined by the coordinates x and y
     * 
     * @param x The COMPUTER y-axis coordinate
     * @param y The COMPUTER x-axis coordinate
     * @return a hit which expresses if the strike is a MISS, STRUCK or sunk a
     *         particular ship
     */
    public Hit sendHit(int x, int y) {
        int value = 0;
        ShipState shipState = new ShipState();
        try {
            shipState = this.getShipGrid()[x][y];
            shipState.addStrike();
        } catch (Exception exception) {
            value = 0; // Error
            return Hit.fromInt(value);
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
        System.out.println("   --  Ship Grid  -- ");

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

        System.out.println();
        System.out.println("   --  Hit Grid  --");

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
     * @param name     the name of the player
     * @param gridSize the size of the grids
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
     * @param name the name of the player
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
    }
}
