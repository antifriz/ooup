package graphicalobjects;

import geometry.Point;
import geometry.Rectangle;
import rendering.Renderer;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.Stack;

/**
 * Created by ivan on 6/16/15.
 */
public class LineSegment extends AbstractGraphicalObject {

    public LineSegment(Point[] hotPoints) {
        super(hotPoints);
    }

    public LineSegment(){
        this(new Point[]{new Point(100, 20), new Point(20, 100)});
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(hotPoints[0],hotPoints[1]);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return 0;
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(hotPoints[0],hotPoints[1]);
    }

    @Override
    public String getShapeName() {
        return "Linija";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment();
    }

    @Override
    public String getShapeID() {
        return null;
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {

    }

    @Override
    public void save(List<String> rows) {

    }
}
