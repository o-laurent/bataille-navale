package ensta;

import java.util.NoSuchElementException;

public enum Hit {
    ERROR(0, "Error"), MISS(-1, "manqué"), STRUCK(-2, "touché"), DESTROYER(2, "Frégate"), SUBMARINE(3, "Sous-marin"),
    BATTLESHIP(4, "Croiseur"), CARRIER(5, "Porte-avion");

    /*
     * *** Attributes
     */
    private int value;
    private String label;

    /***
     * Valued constructor
     * 
     * @param value state of the hit as an int
     * @param label state of the hit as a string
     */
    Hit(int value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * Public method to create a Hit from a value
     * 
     * @param value the value of the future hit
     * @return the hit
     * @throws Exception if the value doesn't fit with a Hit
     */
    public static Hit fromInt(int value) {
        for (Hit hit : Hit.values()) {
            if (hit.value == value) {
                return hit;
            }
        }
        throw new NoSuchElementException("no enum for value " + value);
    }

    /**
     * Public method to get the value of the hit (GETTER)
     * 
     * @return the value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Public method to stringify the Object
     * 
     * @return the corresponding string
     */
    public String toString() {
        return this.label;
    }
};
