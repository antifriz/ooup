package model.document.state;

import geometry.Point;
import graphicalobjects.GraphicalObject;
import rendering.Renderer;

import java.awt.event.KeyEvent;

/**
 * Created by ivan on 6/16/15.
 */
public final class IdleState implements State {
    public IdleState() {
        System.out.println("IdleState");
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

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
