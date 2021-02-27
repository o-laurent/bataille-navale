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
    public Playing playing;
    private Player player1;
    private Player player2;
    private Scanner sin;

    /*
     * *** Constructeurs
     */
    public Game() {
        this.playing = new Playing(1);
    }

    public Game init(boolean twoPlayers) {

        if (!loadSave()) {
            if (!twoPlayers) {
                this.sin = new Scanner(System.in);
                // init attributes
                System.out.println("Entre ton nom:");
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
                this.sin = new Scanner(System.in);

                System.out.println("\033[0;1m" + "Joueur 1: Entre ton nom:" + "\033[0m"); // Bold for UNIX
                String name1 = this.sin.nextLine();
                Board b1 = new Board(name1);

                System.out.println("\033[0;1m" + "Joueur 2: Entre ton nom:" + "\033[0m");
                String name2 = this.sin.nextLine();
                Board b2 = new Board(name2);

                this.player1 = new Player(b1, b2, createDefaultShips());
                this.player2 = new Player(b2, b1, createDefaultShips());

                // place player ships
                System.out.println("\033[0;1m" + b1.getName() + ", place tes bateaux" + "\033[0m");
                sleep(2000);
                b1.print();
                player1.putShips();
                clearTerminal();

                System.out.println("\033[0;1m" + b2.getName() + ", place tes bateaux" + "\033[0m");
                b2.print();
                player2.putShips();
                clearTerminal();
            }
        }
        return this;
    }

    /*
     * *** Méthodes
     */
    public void run(boolean twoPlayers) {
        if (!twoPlayers) {
            int[] coords = new int[2];
            Board b1 = this.player1.board;
            Hit hit;
            System.out.println("\033[0;1m" + b1.getName() + ", à toi de jouer !" + "\033[0m");
            sleep(2000);
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
            System.out.println("\033[0;1m" + String.format("joueur %d gagne", player1.lose ? 2 : 1) + "\033[0m");
            sin.close();
        } else {
            int[] coords = new int[2];
            Board b1 = this.player1.board;
            Board b2 = this.player2.board;
            Hit hit;

            // main loop

            boolean done;
            do {
                clearTerminal();
                System.out.println("\033[0;1m" + b1.getName() + " voici tes grilles." + "\033[0m");
                b1.print();
                hit = this.player1.sendHit(coords);

                boolean strike = hit != Hit.MISS;
                try {
                    b1.setHit(strike, coords[0], coords[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                done = updateScore();

                b1.print();

                System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));
                sleep(2500);

                if (!strike)
                    this.playing.setPlayer(2);
                save();

                if (!done && !strike) {
                    clearTerminal();
                    System.out
                            .println("\033[0;1m" + b1.getName() + ", tourne l'écran vers " + b2.getName() + "\033[0m");
                    sleep(5000);
                    clearTerminal();
                    sleep(2500);
                    System.out.println("\033[0;1m" + b2.getName() + " voici tes grilles." + "\033[0m");

                    do {
                        b2.print();
                        hit = player2.sendHit(coords);

                        strike = hit != Hit.MISS;

                        System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                        done = updateScore();

                        if (!done) {
                            save();
                            if (!strike)
                                this.playing.setPlayer(1);

                        }
                    } while (strike && !done);
                    sleep(2500);
                    clearTerminal();
                    System.out
                            .println("\033[0;1m" + b2.getName() + ", tourne l'écran vers " + b1.getName() + "\033[0m");
                    sleep(5000);
                    clearTerminal();
                    sleep(2500);
                }

            } while (!done);

            SAVE_FILE.delete();
            System.out.println("\033[0;1m" + String.format("joueur %d gagne", player1.lose ? 2 : 1) + "\033[0m");
            sin.close();
        }

    }

    private void save() {
        try {
            if (!SAVE_FILE.exists()) {
                SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
            }
            OutputStream os = new FileOutputStream(SAVE_FILE);
            ObjectOutput o = new ObjectOutputStream(os);
            o.writeObject(this.playing);
            if (this.playing.getPlayer() == 1) {
                o.writeObject(player1);
                o.writeObject(player2);
            } else {
                o.writeObject(player2);
                o.writeObject(player1);
            }
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
                this.playing = (Playing) oi.readObject();
                this.playing = new Playing(1);
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

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String args[]) {
        boolean twoPlayers = false;
        if (args.length == 0) {
            System.out.println("Vous pouvez fournir en argument 2p pour jouer en multijoueur local.");
        } else {
            twoPlayers = (args[0].equals("2p") || args[0].equals("2players") || args[0].equals("twoPlayers"));
            System.out.println("Mode 2 joueurs activé.");
        }

        new Game().init(twoPlayers).run(twoPlayers);
    }
}
