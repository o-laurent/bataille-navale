package ensta;

import java.io.Serializable;
import java.util.*;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.Orientation;

public class BattleShipsAI implements Serializable {

    /*
     * ** Attributs
     */

    /**
     * grid size.
     */
    private final int size;

    /**
     * My board. My ships have to be put on this one.
     */
    private final IBoard board;

    /**
     * My opponent's board. My hits go on this one to strike his ships.
     */
    private final IBoard opponent;

    /**
     * Coords of last known strike. Would be a good idea to target next hits around
     * this point.
     */
    private int lastStrike[];

    /**
     * If last known strike lead me to think the underlying ship has vertical
     * placement.
     */
    private Boolean lastVertical;

    /*
     * ** Constructeur
     */

    /**
     *
     * @param myBoard       board where ships will be put.
     * @param opponentBoard Opponent's board, where hits will be sent.
     */
    public BattleShipsAI(IBoard myBoard, IBoard opponentBoard) {
        this.board = myBoard;
        this.opponent = opponentBoard;
        this.size = board.getSize();
    }

    /*
     * ** Méthodes publiques
     */

    /**
     * Put the ships on owned board.
     * 
     * @param ships the ships to put
     */
    public void putShips(AbstractShip ships[]) {
        int x, y;
        Orientation o;
        Random rand = new Random();
        Orientation[] orientations = Orientation.values();

        for (AbstractShip s : ships) {
            do {
                x = rand.nextInt(size);
                y = rand.nextInt(size);
                o = orientations[rand.nextInt(4)];
                s.setOrientation(o);
            } while (!canPutShip(s, x+1, y+1));
            
            try {
                board.putShip(s, x+1, y+1);
            } catch (Exception err) {
                System.out.println(err);
                System.out.println("Development error");
            }
        }

    }

    /**
     *
     * @param coords array must be of size 2. Will hold the coord of the send hit.
     * @return the status of the hit.
     */
    public Hit sendHit(int[] coords) {
        // TRUE COORDINATES !!!! [0, size-1]
        int res[] = null;
        if (coords == null || coords.length < 2) {
            throw new IllegalArgumentException("must provide an initialized array of size 2");
        }

        // already found strike & orientation?
        if (lastVertical != null) {
            if (lastVertical) {
                res = pickVCoord();
            } else {
                res = pickHCoord();
            }

            if (res == null) {
                // no suitable coord found... forget last strike.
                lastStrike = null;
                lastVertical = null;
            }
        } else if (lastStrike != null) {
            // if already found a strike, without orientation
            // try to guess orientation
            res = pickVCoord();
            if (res == null) {
                res = pickHCoord();
            }
            if (res == null) {
                // no suitable coord found... forget last strike.
                lastStrike = null;
            }
        }

        if (lastStrike == null) {
            res = pickRandomCoord();
        }

        Hit hit = this.opponent.sendHit(res[0], res[1]);

        try {
            this.board.setHit(hit != Hit.MISS, res[0], res[1]);
        } catch (Exception err) {
            System.out.println(err);
            System.out.println("Development error");
        }
        if (hit != Hit.MISS) {
            if (lastStrike != null) {
                lastVertical = guessOrientation(lastStrike, res);
            }
            lastStrike = res;
        }

        coords[0] = res[0];
        coords[1] = res[1];
        return hit;
    }

    /*
     * *** Méthodes privées
     */

    private boolean canPutShip(AbstractShip ship, int x, int y) {
        Orientation o = ship.getOrientation();
        int dx = 0, dy = 0;
        if (o == Orientation.EAST) {
            if (y-1 + ship.getSize()-1 >= this.size) {
                return false;
            }
            dy = 1;
        } else if (o == Orientation.SOUTH) {
            if ((x-1) + (ship.getSize()-1) >= this.size) {
                return false;
            }
            dx = 1;
        } else if (o == Orientation.NORTH) {
            if ((x-1) - (ship.getSize()-1) < 0) {
                return false;
            }
            dx = -1;
        } else if (o == Orientation.WEST) {
            if ((y-1) - (ship.getSize()-1) < 0) {
                return false;
            }
            dy = -1;
        }

        int ix = x;
        int iy = y;

        for (int i = 0; i < ship.getSize(); ++i) {
            try {
                if (board.hasShip(ix, iy)) {
                    return false;
                }
            } catch (Exception err) {
                System.out.println(err);
                System.out.println("Development error");
                return false;
            }
            ix += dx;
            iy += dy;
        }

        return true;
    }

    private boolean guessOrientation(int[] c1, int[] c2) {
        return c1[0] == c2[0];
    }

    private boolean isUndiscovered(int x, int y) {
        try {
            return x >= 0 && x < size && y >= 0 && y < size && board.getHit(x, y) == null;
        } catch (Exception err) {
            System.out.println(err);
            System.out.println("Development error");
            return false;
        }
    }

    private int[] pickRandomCoord() {
        Random rnd = new Random();
        int x;
        int y;

        do {
            x = rnd.nextInt(size);
            y = rnd.nextInt(size);
        } while (!isUndiscovered(x, y));

        return new int[] { x, y };
    }

    /**
     * pick a coord verically around last known strike
     * 
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickVCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int iy : new int[] { y - 1, y + 1 }) {
            if (isUndiscovered(x, iy)) {
                return new int[] { x, iy };
            }
        }
        return null;
    }

    /**
     * pick a coord horizontally around last known strike
     * 
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickHCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int ix : new int[] { x - 1, x + 1 }) {
            if (isUndiscovered(ix, y)) {
                return new int[] { ix, y };
            }
        }
        return null;
    }
}
