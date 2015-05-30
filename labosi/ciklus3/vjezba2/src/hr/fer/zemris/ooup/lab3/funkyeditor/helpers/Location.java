package hr.fer.zemris.ooup.lab3.funkyeditor.helpers;

/**
 * Created by ivan on 5/28/15.
 */
public class Location implements Comparable{
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

    public void addX(int i){
        this.x+=i;
    }
    public void addY(int i){
        this.y+=i;
    }

    public void incX(){
        this.x++;
    }
    public void incY(){
        this.y++;
    }
    public void decX(){
        this.x--;
    }
    public void decY(){
        this.y--;
    }

    @Override
    public String toString() {
        return "(" +
                + x + ","
                + y +
                ")";
    }

    public Location copy(){
        return new Location(this.x,this.y);
    }



    public Location(int x, int y) {
        this.x = x;
        this.y=y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        assert this.getClass().isInstance(o);
        Location l2 = (Location)o;

        return equals(l2) ? 0 : getY() < l2.getY() ? -1 : getY() == l2.getY() ? getX() < l2.getX() ? -1 : getX() == l2.getX() ? 0 : 1 : 1;
    }
}
