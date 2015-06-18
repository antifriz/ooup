package model.document.state;

import geometry.GeometryUtil;
import geometry.Point;
import geometry.Rectangle;
import graphicalobjects.GraphicalObject;
import model.document.DocumentModel;
import org.w3c.dom.css.Rect;
import rendering.Renderer;

import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * Created by ivan on 6/16/15.
 */
public final class SelectShapeState implements State {
    private DocumentModel documentModel;

    public SelectShapeState(DocumentModel documentModel) {
        this.documentModel = documentModel;
        System.out.println("SelectShapeState");
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        documentModel.selectObject(mousePoint, !ctrlDown);
        lastPoint = mousePoint;
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
    }

    Point lastPoint;

    @Override
    public void mouseDragged(Point mousePoint) {
        GraphicalObject go =documentModel.isSingleSelection();
        if(go ==null) return;
        if(go!=documentModel.findSelectedGraphicalObject(mousePoint))
            return;
        int idx =documentModel.findSelectedHotPoint(go,mousePoint);
        if(idx!=-1)
            go.setHotPoint(idx, mousePoint);
        else
        {
            go.translate(mousePoint.difference(lastPoint));
            lastPoint = mousePoint;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                for (GraphicalObject go: documentModel.getSelectedObjects())
                    go.translate(new Point(0,-1));
                break;
            case KeyEvent.VK_DOWN:
                for (GraphicalObject go: documentModel.getSelectedObjects())
                    go.translate(new Point(0,1));
                break;
            case KeyEvent.VK_LEFT:
                for (GraphicalObject go: documentModel.getSelectedObjects())
                    go.translate(new Point(-1,0));
                break;
            case KeyEvent.VK_RIGHT:
                for (GraphicalObject go: documentModel.getSelectedObjects())
                    go.translate(new Point(1,0));
                break;
            case KeyEvent.VK_EQUALS:
                if(!ke.isShiftDown()) break;
                for (GraphicalObject go: documentModel.getSelectedObjects())
                    documentModel.increaseZ(go);
                break;
            case KeyEvent.VK_MINUS:
                for (GraphicalObject go: documentModel.getSelectedObjects())
                    documentModel.decreaseZ(go);
                break;
            case KeyEvent.VK_G:
                documentModel.groupSelectedObjects();
                break;
            case KeyEvent.VK_U:
                documentModel.ungroupSelectedObjects();
                break;
            default:
                break;
        }
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if(!go.isSelected()) return;

        drawRect(r, go.getBoundingBox());

        if(documentModel.isSingleSelection()!=null)
        {
            Iterator<Point> it = go.getHotPointsIterator();
            while (it.hasNext())
                drawHotpoint(r, it.next());
        }
    }

    private void drawRect(Renderer r, Rectangle rect) {
        Point topLeft = new Point(rect.getX(), rect.getY());
        Point topRight = new Point(rect.getX() + rect.getWidth(), rect.getY());
        Point bottomLeft = new Point(rect.getX(), rect.getY() + rect.getHeight());
        Point bottomRight = new Point(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
        r.drawLine(topLeft, topRight);
        r.drawLine(topRight, bottomRight);
        r.drawLine(bottomRight, bottomLeft);
        r.drawLine(bottomLeft, topLeft);
    }

    private void drawHotpoint(Renderer r, Point hotPoint) {
        drawRect(r, new Rectangle(hotPoint.getX() - 5, hotPoint.getY() - 5, 10, 10));
    }

    @Override
    public void afterDraw(Renderer r) {

    }

    @Override
    public void onLeaving() {
        documentModel.deselectAll();
        documentModel.notifyListeners();
    }
}
