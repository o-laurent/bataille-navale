// Object which remembers the number of the current player
package ensta;

import java.io.Serializable;

public class Playing implements Serializable {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 100L;

    /**
     * The number of the current player
     */
    public int playing;

    /**
     * Method to change the current player (SETTER)
     * 
     * @param player the number of the current player
     */
    public void setPlayer(int player) {
        playing = player;
    }

    /**
     * Method to get the number of the current player (GETTER)
     * 
     * @return the number of the current player
     */
    public int getPlayer() {
        return playing;
    }

    /**
     * Valued Constructor
     * 
     * @param player int representing the number of the player who is playing
     */
    public Playing(int player) {
        this.playing = player;
    }
}
