package graphicalobjects;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import geometry.GeometryUtil;
import geometry.Point;
import geometry.Rectangle;
import graphicalobjects.GraphicalObject;
import graphicalobjects.GraphicalObjectListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ivan on 6/16/15.
 */
public abstract class AbstractGraphicalObject implements GraphicalObject {

    Point[] hotPoints;
    boolean[] hotPointSelected;
    boolean selected;
    List<GraphicalObjectListener> listeners = new ArrayList<>();

    public AbstractGraphicalObject(Point[] hotPoints) {

        this.hotPoints = hotPoints;
        this.hotPointSelected = new boolean[hotPoints.length];
    }
    @Override
    public Point getHotPoint(int index) {
        return hotPoints[index];
    }

    @Override
    public void setHotPoint(int index, Point point) {

        hotPoints[index] = point;
        notifyListeners();

    }

    @Override
    public int getNumberOfHotPoints() {
        return hotPoints.length;
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return GeometryUtil.distanceFromPoint(hotPoints[index], mousePoint);
    }

    @Override
    public Iterator<Point> getHotPointsIterator() {
        return new Iterator<Point>() {
            int i =0;
            @Override
            public boolean hasNext() {
                return i<hotPoints.length;
            }

            @Override
            public Point next() {
                assert i<hotPoints.length;
                return hotPoints[i++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Rectangle getBoundingBox() {
        int xmin = hotPoints[0].getX();
        int xmax = hotPoints[0].getX();
        int ymin = hotPoints[0].getY();
        int ymax = hotPoints[0].getY();
        for(Point hp : hotPoints)
        {
            xmin = Math.min(xmin,hp.getX());
            xmax = Math.max(xmax, hp.getX());
            ymin = Math.min(ymin,hp.getY());
            ymax = Math.max(ymax, hp.getY());
        }
        return new Rectangle(xmin,ymin, xmax-xmin,ymax-ymin);
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointSelected[index];
    }
    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointSelected[index] = selected;
        notifySelectionListeners();
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        notifySelectionListeners();
        this.selected= selected;
    }



    @Override
    public void translate(Point delta) {
        for (int i = 0; i < hotPoints.length; i++) {
            hotPoints[i] = hotPoints[i].translate(delta);
        }
        notifyListeners();
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }


    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    void notifyListeners(){
        for (GraphicalObjectListener l : listeners)
            l.graphicalObjectChanged(this);
    }
    void notifySelectionListeners(){
        for (GraphicalObjectListener l : listeners)
            l.graphicalObjectSelectionChanged(this);
    }

}
