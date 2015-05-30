package hr.fer.zemris.ooup.lab3.funkyeditor.model;

import hr.fer.zemris.ooup.lab3.funkyeditor.helpers.Location;

/**
 * Created by ivan on 5/28/15.
 */
public interface CursorObserver {
    void onUpdateCursorLocation(Location loc);
}
