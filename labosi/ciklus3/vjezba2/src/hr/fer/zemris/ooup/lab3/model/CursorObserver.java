package hr.fer.zemris.ooup.lab3.model;

import hr.fer.zemris.ooup.lab3.helpers.Location;

/**
 * Created by ivan on 5/28/15.
 */
public interface CursorObserver {
    void onUpdateCursorLocation(Location loc);
}