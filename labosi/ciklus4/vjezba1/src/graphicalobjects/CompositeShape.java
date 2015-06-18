package graphicalobjects;

import geometry.Point;
import geometry.Rectangle;
import rendering.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Created by ivan on 6/18/15.
 */
public class CompositeShape extends AbstractGraphicalObject{

    List<GraphicalObject> components = new ArrayList<>();

    public CompositeShape() {
        super(new Point[]{});
    }

    public void add(GraphicalObject go){
        components.add(go);
        notifyListeners();
    }
    public void remove(GraphicalObject go) {
        components.remove(go);
        notifyListeners();
    }

    @Override
    public Rectangle getBoundingBox() {
        ListIterator<GraphicalObject> it = components.listIterator();
        assert it.hasNext();
        Rectangle bb = it.next().getBoundingBox();
        while (it.hasNext())
            bb = bb.union(it.next().getBoundingBox());
        return bb;
    }

    @Override
    public void render(Renderer r) {
        for (GraphicalObject go: components)
            go.render(r);
    }

    public List<GraphicalObject> getComponents() {
        return components;
    }

    @Override
    public void translate(Point delta) {
        for (GraphicalObject go:components)
            go.translate(delta);
    }

    @Override
    public String getShapeName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public GraphicalObject duplicate() {
        CompositeShape c = new CompositeShape();
        for (GraphicalObject go : components)
            c.add(go.duplicate());
        return c;
    }

    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        CompositeShape o = new CompositeShape();
        for (int i = 0; i < Integer.parseInt(data); i++)
            o.add(stack.pop());
        stack.push(o);
    }

    @Override
    public void save(List<String> rows) {
        for (GraphicalObject go : components)
            go.save(rows);
        rows.add(String.format("%s %d",getShapeID(),components.size()));
    }
}
