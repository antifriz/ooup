package model.document.state;

import geometry.Point;
import graphicalobjects.GraphicalObject;
import model.document.DocumentModel;
import rendering.Renderer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ivan on 6/18/15.
 */
public class EraserState implements State {
    List<Point> eraserPath = new ArrayList<>();
    private DocumentModel documentModel;

    public EraserState(DocumentModel documentModel) {
        System.out.println("EraserState");
        this.documentModel = documentModel;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        documentModel.erase(eraserPath);
        eraserPath.clear();
        documentModel.notifyListeners();
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        eraserPath.add(new Point(mousePoint));
        documentModel.notifyListeners();
    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
    }

    @Override
    public void afterDraw(Renderer r) {
        ListIterator<Point> it = eraserPath.listIterator();
        if(!it.hasNext()) return;
        Point a = it.next();
        while (it.hasNext())
        {
            Point b =it.next();
            r.drawLine(a,b);
            a = b;
        }
    }

    @Override
    public void onLeaving() {
        eraserPath.clear();
        documentModel.notifyListeners();
    }
}
