import geometry.*;
import geometry.Point;
import graphicalobjects.CompositeShape;
import graphicalobjects.GraphicalObject;
import graphicalobjects.LineSegment;
import graphicalobjects.Oval;
import gui.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 6/16/15.
 */
public class Main {
    public static void main(String[] args) {
//        CompositeShape cs = new CompositeShape();
//        cs.add(new LineSegment(new Point[]{new Point(100,110),new Point(200,210)}));
//        cs.add(new LineSegment(new Point[]{new Point(150,110),new Point(250,210)}));
//        objects.add(cs);

        GUI gui = new GUI();
        gui.setBounds(100, 100, 500, 400);
        gui.setVisible(true);

       /* EventQueue.invokeLater(() -> {
            try {
                FunkyFrame frame = new FunkyFrame();
                frame.setBounds(100, 100, 500, 400);
                frame.setVisible(true);
                //frame.initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
    }
}
