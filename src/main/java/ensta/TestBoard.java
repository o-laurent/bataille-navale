package ensta;

import ensta.AbstractShip.BattleShip;
import ensta.AbstractShip.Carrier;
import ensta.AbstractShip.Destroyer;
import ensta.AbstractShip.Orientation;


public class TestBoard {
    public static void main(String[] args) {
        Board testBoard = new Board("Test");
        Carrier carrier = new Carrier(Orientation.EAST);
        BattleShip bShip = new BattleShip(Orientation.SOUTH);
        Destroyer destr = new Destroyer(Orientation.SOUTH);
        try {
            testBoard.putShip(carrier, 1, 1);
            testBoard.putShip(bShip, 2, 1);
            testBoard.putShip(destr, 9, 1);
        }
        catch (Exception errException) {
            System.out.println(errException);
        }
        testBoard.print();
    }
}
