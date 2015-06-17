package model.document.state;

import geometry.Point;
import graphicalobjects.GraphicalObject;
import model.document.DocumentModel;
import rendering.Renderer;

import java.awt.event.KeyEvent;

/**
 * Created by ivan on 6/16/15.
 */
public final class AddShapeState implements State {

    private GraphicalObject prototype;
    private DocumentModel model;

    public AddShapeState(DocumentModel model, GraphicalObject prototype) {
        this.model = model;
        this.prototype = prototype;
        System.out.println("AddShapeState");
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject go =  prototype.duplicate();
        go.translate(mousePoint);
        System.out.println("Žigosanje @ "+mousePoint);
        model.addGraphicalObject(go);
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

    }

    @Override
    public void mouseDragged(Point mousePoint) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {

    }

    @Override
    public void afterDraw(Renderer r) {

    }

    @Override
    public void onLeaving() {

    }
}