package ensta.AbstractShip;

public class BattleShip extends AbstractShip{
    public BattleShip() {
        super("Battleship", 'B', 4, null);
    }

    public BattleShip(Orientation orient) {
        super("Battleship", 'B', 4, orient);
    }
}
