package ensta;

import java.io.Serializable;

public class Playing implements Serializable {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 100L;

    public int playing; // number of the playing player

    public void setPlayer(int player) {
        playing = player;
    }

    public int getPlayer() {
        return playing;
    }

    /*
     * Valued Constructor
     */
    public Playing(int player) {
        this.playing = player;
    }
}
