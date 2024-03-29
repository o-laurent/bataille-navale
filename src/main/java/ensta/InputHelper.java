package ensta;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public final class InputHelper implements Serializable {
    private static final long serialVersionUID = 10L;

    /**
     * The Default constructor
     */
    private InputHelper() {
    }

    /*
     * Classe ShipInput, interne à InputHelper
     */
    public static class ShipInput {
        public String orientation;
        public int x;
        public int y;
    }

    /*
     * Classe CoordInput, interne à InputHelper
     */
    public static class CoordInput {
        public int x;
        public int y;
    }

    /*
     * Méthodes de la classe InputHelper
     */
    public static ShipInput readShipInput() {
        @SuppressWarnings("resource")
        Scanner sin = new Scanner(System.in);
        ShipInput res = new ShipInput();
        String[] validOrientations = { "n", "s", "e", "w" }; // North, South, East, West
        boolean done = false;

        do {
            try {
                String[] in = sin.nextLine().toLowerCase().split(" ");
                if (in.length == 2) {
                    String coord = in[0];
                    if (Arrays.asList(validOrientations).contains(in[1])) {
                        res.orientation = in[1];
                        res.y = coord.charAt(0) - 'a';
                        res.x = Integer.parseInt(coord.substring(1, coord.length())) - 1;
                        done = true;
                    }
                }
            } catch (Exception e) {
                // nop
            }

            if (!done) {
                System.err.println("Format incorrect! Entrez la position sous forme 'A0 n'");
            }
        } while (!done && sin.hasNextLine());

        return res;
    }

    /**
     * Read input coordinates from the user
     * 
     * @return
     */
    public static CoordInput readCoordInput() {
        @SuppressWarnings("resource")
        Scanner sin = new Scanner(System.in);
        CoordInput res = new CoordInput();
        boolean done = false;

        do {
            try {
                String coord = sin.nextLine().toLowerCase();
                res.y = coord.charAt(0) - 'a';
                res.x = Integer.parseInt(coord.substring(1, coord.length())) - 1;
                done = true;
            } catch (Exception e) {
                // nop
            }

            if (!done) {
                System.err.println("Format incorrect! Entrez la position sous forme 'A0'");
            }
        } while (!done && sin.hasNextLine());

        return res;
    }
}
