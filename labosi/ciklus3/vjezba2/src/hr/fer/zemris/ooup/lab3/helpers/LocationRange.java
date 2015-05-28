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

    public LocationRange(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

}
