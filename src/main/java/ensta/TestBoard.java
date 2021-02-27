package ensta;

import ensta.AbstractShip.BattleShip;
import ensta.AbstractShip.Carrier;
import ensta.AbstractShip.Destroyer;
import ensta.AbstractShip.Orientation;


public class TestBoard {
    public static void main(String[] args) {
        Board player1Board = new Board("Player 1");
        Board player2Board = new Board("Player 2");
        Carrier carrier = new Carrier(Orientation.EAST);
        BattleShip bShip = new BattleShip(Orientation.SOUTH);
        Destroyer destr = new Destroyer(Orientation.SOUTH);
        try {
            player1Board.putShip(carrier, 1, 1);
            player1Board.putShip(bShip, 2, 1);
            player1Board.putShip(destr, 9, 1);
        }
        catch (Exception errException) {
            System.out.println(errException);
        }
    
        System.out.println(player1Board.sendHit(1, 1));
        System.out.println(player1Board.sendHit(1, 2));
        System.out.println(player1Board.sendHit(1, 3));
        System.out.println(player1Board.sendHit(1, 4));
        System.out.println(player1Board.sendHit(1, 5));
        System.out.println(player1Board.sendHit(5, 5));
        player1Board.print();
    }
}
