package ensta;

import java.io.Serializable;
import java.util.List;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.Orientation;

public class Player {
    /*
     * ** Attributs
     */
    protected Board board;
    protected Board opponentBoard;
    protected int destroyedCount;
    protected AbstractShip[] ships;
    protected boolean lose;

    /*
     * ** Constructeur
     */
    public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships.toArray(new AbstractShip[0]);
        this.opponentBoard = opponentBoard;
    }

    /*
     * ** Méthodes
     */

    /**
     * Read keyboard input to get ships coordinates. Place ships on given
     * coodrinates.
     */
    public void putShips() {
        boolean done = false;
        int i = 0;

        do {
            AbstractShip s = ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getFullname(), s.getSize());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            ++i;

            try {
                if (res.orientation.charAt(0) == 'n') {
                    s.setOrientation(Orientation.NORTH);
                }
                
                else if (res.orientation.charAt(0) == 's') {
                    s.setOrientation(Orientation.SOUTH);
                }
                
                else if (res.orientation.charAt(0) == 'e') {
                    s.setOrientation(Orientation.EAST);
                }
                
                else if (res.orientation.charAt(0) == 'w') {
                    s.setOrientation(Orientation.WEST);
                }
                else {
                    System.out.println("Error. Please type n, s, e or w.");
                }
                int x = res.x;
                int y = res.y;

                board.putShip(s, x, y);
            } catch (Exception exc) {
                --i;
            }
            done = (i == 5);
            
            board.print();
        } while (!done);
    }

    public Hit sendHit(int[] coords) {
        boolean done = false;
        Hit hit = null;

        do {
            System.out.println("Où souhaitez-vous frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            
            hit = this.opponentBoard.sendHit(hitInput.x, hitInput.y);
            if (hit != Hit.ERROR) done = true;
            else System.out.println("Tir refusé ! Les coordonnées sont impossibles ou zone déjà touchée.");
            // TODO : Game expects sendHit to return BOTH hit result & hit coords.
            // return hit is obvious. But how to return coords at the same time ?
            
        } while (!done);

        return hit;
    }

    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
}
