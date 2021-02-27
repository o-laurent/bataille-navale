package ensta;

import java.io.Serializable;
import java.util.List;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.Orientation;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
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
            AbstractShip s = this.ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getFullname(), s.getSize());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            ++i;
            int x = 0;
            int y = 0;

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
                } else {
                    System.out.println("Error. Please type n, s, e or w.");
                }
                y = res.y;
                x = res.x;

                this.board.putShip(s, x + 1, y + 1);
            } catch (Exception exc) {
                System.out.println("Erreur dans le placement du bâteau: x=" + x + " y=" + y);
                --i;
            }
            done = (i == 5);

            this.board.print();
        } while (!done);
    }

    public Hit sendHit(int[] coords) {
        boolean done = false;
        Hit hit = null;
        InputHelper.CoordInput hitInput;

        do {
            System.out.println("Où souhaitez-vous frapper?");
            hitInput = InputHelper.readCoordInput();

            System.out.println("x:" + hitInput.x + " y:" + hitInput.y);
            hit = this.opponentBoard.sendHit(hitInput.x, hitInput.y);
            if (hit != Hit.ERROR)
                done = true;
            else
                System.out.println("Tir refusé ! Les coordonnées sont impossibles ou zone déjà touchée.");

        } while (!done);
        coords[0] = hitInput.x;
        coords[1] = hitInput.y;
        return hit;
    }

    public AbstractShip[] getShips() {
        return this.ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
}
