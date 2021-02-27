package ensta.AbstractShip;

public class AbstractShip {
    private Character label;
    private String fullname;
    private int size;
    private Orientation orientation;

    private int strikeCount;

    public Character getLabel() {
        return this.label;
    }

    public String getFullname() {
        return this.fullname;
    }

    public int getSize() {
        return this.size;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Orientation orient) {
        this.orientation = orient;
    }

    public void addStrike() {
        this.strikeCount++;
    }

    public int howStruck() {
        return this.strikeCount;
    }
    public boolean isSunk()
    {
        if (size == strikeCount) {
            return true;
        } else {
            return false;
        }
    }

    public AbstractShip(String fullname, Character label, int size, Orientation orient) {
        this.fullname = fullname;
        this.label = label;
        this.size = size;
        this.orientation = orient;
        this.strikeCount = 0;
    }
}
