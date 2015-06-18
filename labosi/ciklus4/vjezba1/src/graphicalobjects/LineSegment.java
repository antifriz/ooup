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
        this(new Point[]{new Point(0, 10), new Point(10, 0)});
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(hotPoints[0],hotPoints[1]);
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
        return "@LINE";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] parts = data.split(" ");
        stack.push(new LineSegment(new Point[]{new Point(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])),new Point(Integer.parseInt(parts[2]),Integer.parseInt(parts[3]))}));
    }

    @Override
    public void save(List<String> rows) {
        rows.add(String.format("%s %d %d %d %d",getShapeID(),hotPoints[0].getX(),hotPoints[0].getY(),hotPoints[1].getX(),hotPoints[1].getY()));
    }
}
