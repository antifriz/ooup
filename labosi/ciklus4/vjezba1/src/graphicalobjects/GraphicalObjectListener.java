package graphicalobjects;

import graphicalobjects.GraphicalObject;

/**
 * Created by ivan on 6/16/15.
 */
public interface GraphicalObjectListener {

    // Poziva se kad se nad objektom promjeni bio što...
    void graphicalObjectChanged(GraphicalObject go);
    // Poziva se isključivo ako je nad objektom promjenjen status selektiranosti
    // (baš objekta, ne njegovih hot-point-a).
    void graphicalObjectSelectionChanged(GraphicalObject go);

}