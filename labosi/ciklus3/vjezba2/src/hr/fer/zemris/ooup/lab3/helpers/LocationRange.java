package hr.fer.zemris.ooup.lab3.helpers;

/**
 * Created by ivan on 5/28/15.
 */
public class LocationRange {

    public Location getFrom() {
        return from;
    }


    public Location getTo() {
        return to;
    }



    private Location from;
    private Location to;

    @Override
    public String toString() {
        return "[ " +
                from +
                " - " + to +
                " ]";
    }

    public LocationRange(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    public Location getLower(){
        return from.compareTo(to)>0?to:from;
    }

    public Location getHigher(){
        return from.compareTo(to)<0?to:from;
    }

    public boolean isSingleLine(){
        return from.getY() == to.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationRange that = (LocationRange) o;

        return from.equals(that.from) && to.equals(that.to);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }
}
