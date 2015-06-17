package gui;

import graphicalobjects.GraphicalObject;
import graphicalobjects.LineSegment;
import graphicalobjects.Oval;
import model.document.DocumentModel;
import model.document.DocumentModelListener;
import model.document.state.AddShapeState;
import model.document.state.IdleState;
import model.document.state.SelectShapeState;
import model.document.state.State;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ivan on 6/16/15.
 */
public class GUI extends JFrame implements DocumentModelListener {
    List<GraphicalObject> objects = new ArrayList<>();
    DocumentModel documentModel;

    Canvas canvas;
    ToolBar toolBar;

    private static final  GraphicalObject[] IMPLEMENTED_OBJECTS = {
            new LineSegment(),
            new Oval()
    };

    private State currentState = new IdleState();

    public GUI(List<GraphicalObject> objects) {

        this.objects = objects;
        documentModel = new DocumentModel();
        for (GraphicalObject go : objects)
            documentModel.addGraphicalObject(go);

        documentModel.addDocumentModelListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new BorderLayout());

        toolBar = new ToolBar();
        panel.add(toolBar, BorderLayout.PAGE_START);

        canvas = new Canvas(this);
        panel.add(canvas, BorderLayout.CENTER);

        addKeyListener(canvas);
        addMouseListener(canvas);
        addMouseMotionListener(canvas);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            currentState.onLeaving();
            currentState = new IdleState();
        }
    }


    public State getCurrentState() {
        return currentState;
    }

    @Override
    public void documentChange() {
        System.out.println("DocumentChanged");
        toolBar.repaint();
        canvas.repaint();
    }

    public int getToolBarHeight() {
        return 57;//toolBar.getHeight();
    }

    public class ToolBar extends JToolBar {

        HashMap<String,JButton> buttonCreatorsMap = new HashMap<>();

        public ToolBar() {

            for(final GraphicalObject go : IMPLEMENTED_OBJECTS)
            {
                JButton button = new JButton(go.getShapeName());
                add(button);
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        GUI.this.currentState = new AddShapeState(documentModel,go);
                    }
                });
            }
            {
                JButton button = new JButton("Selektiraj");
                add(button);
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        GUI.this.currentState = new SelectShapeState(documentModel);
                    }
                });
            }
        }
    }
}
