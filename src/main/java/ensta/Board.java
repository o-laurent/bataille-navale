package ensta;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.Orientation;

public class Board implements IBoard {
    private String name;
    private int gridSize;
    private Character[][] shipGrid;
    private Boolean[][] hitGrid;

    public void putShip(AbstractShip ship, int x, int y) throws Exception {
        int gridSize = getSize();
        if (x < 0 || y < 0 || x > gridSize + 1 || y > gridSize + 1) {
            throw new Exception("The coordinates are wrong.");
        }
        Character label = ship.getLabel();
        Orientation orient = ship.getOrientation();
        int size = ship.getSize();
        switch (orient) {
            case EAST:
                if (x + (size - 1) > size + 1) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[y + 1][x + i + 1] = label;
                }
                break;
            case WEST:
                if (x - (size - 1) < 0) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[y + 1][x - i + 1] = label;
                }
                break;
            case SOUTH:
                if (y + (size - 1) > size + 1) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[y + i + 1][x + 1] = label;
                }
                break;

            case NORTH:
                if (y + (size - 1) < 0) {
                    throw new Exception("The coordinates are wrong.");
                }
                for (int i = 0; i < size; ++i) {
                    shipGrid[y - i + 1][x + 1] = label;
                }
                break;
        }

    }

    public boolean hasShip(int x, int y) throws Exception {
        int size = getSize();
        if (x < 1 || y < 1 || x > size || y > size) {
            throw new Exception("The coordinates are wrong.");
        }
        return !(shipGrid[y - 1][x - 1] == Character.MIN_VALUE);
    }

    public void setHit(boolean hit, int x, int y) throws Exception {
        int size = getSize();
        if (x < 1 || y < 1 || x > size || y > size) {
            throw new Exception("The coordinates are wrong.");
        }
        hitGrid[y - 1][x - 1] = true;
    }

    public Boolean getHit(int x, int y) throws Exception {
        int size = getSize();
        if (x < 1 || y < 1 || x > size || y > size) {
            throw new Exception("The coordinates are wrong.");
        }
        return hitGrid[y - 1][x - 1];
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
    public Character[][] getShip() {
        return this.shipGrid;
    }

    /**
     * Get the whole hitGrid
     * 
     * @return the hit grid
     */
    public Boolean[][] getHit() {
        return this.hitGrid;
    }

    /**
     * Print the two boards
     */
    public void print() {
        System.out.println("Ship Grid");

        System.out.print("  "); // lineNb space
        for (Character c = 'A'; c < 'A' + getSize(); c++) {
            System.out.print(c + " ");
        }
        int lineNb = 1;
        for (Character[] x : getShip()) {
            System.out.print(lineNb++ + " ");
            for (Character y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
        System.out.println("Hit Grid");
        for (Character c = 'A'; c < 'A' + getSize(); c++) {
            System.out.print(c + " ");
        }
        lineNb = 1;
        for (Boolean[] x : getHit()) {
            System.out.print(lineNb++ + " ");
            for (Boolean y : x) {
                if (y) {
                    System.out.print("x ");
                } else {
                    System.out.print(". ");
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
        this.shipGrid = new Character[gridSize][gridSize];
        this.hitGrid = new Boolean[gridSize][gridSize];
    }

    /**
     * Valued contructor
     * 
     * @param name
     */
    public Board(String name) {
        this.name = name;
        this.shipGrid = new Character[10][10];
        this.hitGrid = new Boolean[10][10];
    }
}
