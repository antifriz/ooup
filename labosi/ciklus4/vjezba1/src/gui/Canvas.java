package gui;

import graphicalobjects.GraphicalObject;
import geometry.Point;
import rendering.Renderer;
import rendering.G2DRendererImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.List;


/**
 * Created by ivan on 6/16/15.
 */
public class Canvas extends JComponent implements KeyListener,MouseListener, MouseMotionListener {
    private GUI gui;

    public Canvas(GUI gui) {
        this.gui = gui;
    }


    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Painting");
        Graphics2D g2d = (Graphics2D) g;
        Renderer r = new G2DRendererImpl(g2d);

        List<GraphicalObject> objects =gui.documentModel.getAllObjects();
        for (GraphicalObject go : objects)
        {
            go.render(r);
            gui.getCurrentState().afterDraw(r, go);
        }
        gui.getCurrentState().afterDraw(r);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        gui.getCurrentState().keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    Point getPoint(MouseEvent mouseEvent){
        return new Point((int) mouseEvent.getPoint().getX(), (int) mouseEvent.getPoint().getY()-gui.getToolBarHeight());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        gui.getCurrentState().mouseDown(getPoint(mouseEvent), mouseEvent.isShiftDown(), mouseEvent.isControlDown());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        gui.getCurrentState().mouseUp(getPoint(mouseEvent), mouseEvent.isShiftDown(), mouseEvent.isControlDown());
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        gui.getCurrentState().mouseDragged(getPoint(mouseEvent));
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
