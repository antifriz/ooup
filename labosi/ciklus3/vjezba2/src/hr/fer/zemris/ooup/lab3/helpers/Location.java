package hr.fer.zemris.ooup.lab3.helpers;

/**
 * Created by ivan on 5/28/15.
 */
public class Location {
    private int x;

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y=y;
    }
}
