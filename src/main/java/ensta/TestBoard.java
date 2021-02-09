package ensta;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.Carrier;
import ensta.AbstractShip.Orientation;


public class TestBoard {
    public void main() {
        Board testBoard = new Board("Test");
        Carrier carrier = new Carrier(EAST)
        testBoard.putShip(carrier, 0, y);
        testBoard.print();
    }
}
