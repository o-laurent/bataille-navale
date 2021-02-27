package ensta;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ensta.AbstractShip.AbstractShip;
import ensta.AbstractShip.BattleShip;
import ensta.AbstractShip.Carrier;
import ensta.AbstractShip.Destroyer;
import ensta.AbstractShip.Submarine;

public class Game {
    /*
     * *** Constante
     */
    public static final File SAVE_FILE = new File("savegame.dat");

    /*
     * *** Attributs
     */
    private Player player1;
    private Player player2;
    private Scanner sin;

    /*
     * *** Constructeurs
     */
    public Game() {
    }

    public Game init() {

        if (!loadSave()) {
            this.sin = new Scanner(System.in);
            // init attributes
            System.out.println("entre ton nom:");
            String name = this.sin.nextLine();

            Board b1 = new Board(name);
            Board b2 = new Board("Ordinateur");

            this.player1 = new Player(b1, b2, createDefaultShips());
            this.player2 = new AIPlayer(b2, b1, createDefaultShips());

            b1.print();
            // place player ships
            player1.putShips();
            player2.putShips();
        } else {

        }
        return this;
    }

    /*
     * *** Méthodes
     */
    public void run() {
        int[] coords = new int[2];
        Board b1 = this.player1.board;
        Hit hit;

        // main loop
        b1.print();
        boolean done;
        do {
            hit = this.player1.sendHit(coords);

            boolean strike = hit != Hit.MISS;
            try {
                b1.setHit(strike, coords[0], coords[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            done = updateScore();
            System.out.println(b1.getName());
            b1.print();

            System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));

            save();

            if (!done && !strike) {
                do {
                    hit = player2.sendHit(coords);

                    strike = hit != Hit.MISS;
                    if (strike) {
                        b1.print();
                    }
                    System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                    done = updateScore();

                    if (!done) {
                        save();
                    }
                } while (strike && !done);
            }

        } while (!done);

        SAVE_FILE.delete();
        System.out.println(String.format("joueur %d gagne", player1.lose ? 2 : 1));
        sin.close();
    }

    private void save() {
        try {
            if (!SAVE_FILE.exists()) {
                SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
            }
            OutputStream os = new FileOutputStream(SAVE_FILE);
            ObjectOutput o = new ObjectOutputStream(os);
            o.writeObject(player1);
            o.writeObject(player2);
            o.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean loadSave() {
        if (SAVE_FILE.exists()) {
            try {
                FileInputStream fi = new FileInputStream(SAVE_FILE);
                ObjectInputStream oi = new ObjectInputStream(fi);
                this.player1 = (Player) oi.readObject();
                this.player2 = (Player) oi.readObject();
                oi.close();
                return true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }

    private boolean updateScore() {
        for (Player player : new Player[] { player1, player2 }) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips()) {
                if (ship.isSunk()) {
                    destroyed++;
                }
            }

            player.destroyedCount = destroyed;
            player.lose = destroyed == player.getShips().length;
            if (player.lose) {
                return true;
            }
        }
        return false;
    }

    private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
        String msg;
        ColorUtil.Color color = ColorUtil.Color.RESET;
        switch (hit) {
            case MISS:
                msg = hit.toString();
                break;
            case STRUCK:
                msg = hit.toString();
                color = ColorUtil.Color.RED;
                break;
            default:
                msg = hit.toString() + " coulé";
                color = ColorUtil.Color.RED;
        }
        msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>", ((char) ('A' + coords[1])),
                (coords[0] + 1), msg);
        return ColorUtil.colorize(msg, color);
    }

    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[] { new Destroyer(), new Submarine(), new Submarine(), new BattleShip(),
                new Carrier() });
    }

    public static void main(String args[]) {
        new Game().init().run();
    }
}
