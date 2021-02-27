package ensta;

import java.util.List;

import ensta.AbstractShip.AbstractShip;

public class AIPlayer extends Player {
    private static final long serialVersionUID = 2L;
    /*
     * ** Attribut
     */
    private BattleShipsAI ai;

    /*
     * ** Constructeur
     */
    public AIPlayer(Board ownBoard, Board opponentBoard, List<AbstractShip> ships) {
        super(ownBoard, opponentBoard, ships);
        this.ai = new BattleShipsAI(ownBoard, opponentBoard);
        this.ships = ships.toArray(new AbstractShip[0]);;
    }

    public void putShips() {
        this.ai.putShips(this.ships); 
    }

    public Hit sendHit(int[] coords) {
        return this.ai.sendHit(coords);
    }

}
