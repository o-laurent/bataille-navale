package ensta;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.BattleShip;
import ensta.AbstractShip.Carrier;
import ensta.AbstractShip.Destroyer;
import ensta.AbstractShip.Submarine;

public class TestGame {
    public static void main(String[] args) {
        Board board = new Board("Unique");

        AbstractShip[] ships = createDefaultShips();

        BattleShipsAI ai = new BattleShipsAI(board, board);
        ai.putShips(ships);

        int counter = 0;
        int tourCounter = 0;
        int[] coords = { 0, 0 };
        Hit hit;
        while (counter != 5) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Tour " + tourCounter++);
            System.out.println();
            board.print();
            hit = ai.sendHit(coords);

            System.out.println();

            switch (hit.getValue()) {
                case -2:
                    System.out.println("    --  Touché  -- ");
                    break;
                case -1:
                    System.out.println("    --  Manqué  -- ");
                    break;
                default:
                    if (hit.getValue() > 0)
                        System.out.println(" --  " + hit + " coulé  -- ");
            }
            if (hit.getValue() > 0) {
                counter++;
            }
        }
        board.print();

    }

    private static AbstractShip[] createDefaultShips() {
        return new AbstractShip[] { new Destroyer(), new Submarine(), new Submarine(), new BattleShip(),
                new Carrier() };
    }
}
