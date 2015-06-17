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
        List<GraphicalObject> objects = new ArrayList<>();

        objects.add(new LineSegment());
        objects.add(new Oval());

        GUI gui = new GUI(objects);
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
