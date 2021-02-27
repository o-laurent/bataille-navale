package ensta.AbstractShip;

public class BattleShip extends AbstractShip {
    private static final long serialVersionUID = 5L;

    public BattleShip() {
        super("Battleship", 'B', 4, null);
    }

    public BattleShip(Orientation orient) {
        super("Battleship", 'B', 4, orient);
    }
}
