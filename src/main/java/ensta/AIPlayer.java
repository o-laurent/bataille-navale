package ensta;

import java.util.List;

import ensta.AbstractShip.AbstractShip;

public class AIPlayer extends Player {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 2L;

    /*
     * ** Attribut
     */
    private BattleShipsAI ai;

    /**
     * ValuedConstructor
     * 
     * @param ownBoard      the AIPlayer's board
     * @param opponentBoard the AIPlayer's opponent's board
     * @param ships         the list of the ships of the AIPlayer
     */
    public AIPlayer(Board ownBoard, Board opponentBoard, List<AbstractShip> ships) {
        super(ownBoard, opponentBoard, ships);
        this.ai = new BattleShipsAI(ownBoard, opponentBoard);
        this.ships = ships.toArray(new AbstractShip[0]);
        ;
    }

    /**
     * A public method which automatically places the AiPlayer's ships.
     */
    public void putShips() {
        this.ai.putShips(this.ships);
    }

    /**
     * A public method which send a hit and updates the coordinates
     * 
     * @param coords A 2D array which will save the coordinates of the board
     */
    public Hit sendHit(int[] coords) {
        return this.ai.sendHit(coords);
    }

}
