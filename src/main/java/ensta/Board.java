package ensta;

public class Board {
    private String name;
    private int gridSize;
    private Character[][] shipGrid;
    private Boolean[][] hitGrid;

    public Board(String name, int gridSize) {
        this.name = name;
        this.gridSize = gridSize;
        this.shipGrid = new Character[gridSize][gridSize];
        this.hitGrid = new Boolean[gridSize][gridSize];
    }

    public Board(String name) {
        this.name = name;
        this.shipGrid = new Character[10][10];
        this.hitGrid = new Boolean[10][10];
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.gridSize;
    }

    public Character[][] getShip() {
        return this.shipGrid;
    }

    public Boolean[][] getHit() {
        return this.hitGrid;
    }

    public void print() {
        System.out.println("Ship Grid");
        for (Character[] x : getShip()) {
            for (Character y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
        System.out.println("Hit Grid");
        for (Character c='A'; c<'A'+getSize(); c++) {
            System.out.print(c+" ");
        }
        for (Boolean[] x : getHit()) {
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
}
