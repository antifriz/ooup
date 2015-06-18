package gui;

import graphicalobjects.CompositeShape;
import graphicalobjects.GraphicalObject;
import graphicalobjects.LineSegment;
import graphicalobjects.Oval;
import model.document.DocumentModel;
import model.document.DocumentModelListener;
import model.document.state.*;
import rendering.SVGRendererImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * Created by ivan on 6/16/15.
 */
public class GUI extends JFrame implements DocumentModelListener {
    List<GraphicalObject> objects = new ArrayList<>();
    DocumentModel documentModel;

    Canvas canvas;
    ToolBar toolBar;

    private static final GraphicalObject[] IMPLEMENTED_OBJECTS = {
            new LineSegment(),
            new Oval()
    };

    private static final Map<String,GraphicalObject> SERIALIZATION_ID_MAP;
    static {
        SERIALIZATION_ID_MAP = new HashMap<>();
        GraphicalObject o = new LineSegment();
        SERIALIZATION_ID_MAP.put(o.getShapeID(), o);
        o=new Oval();
        SERIALIZATION_ID_MAP.put(o.getShapeID(), o);
        o=new CompositeShape();
        SERIALIZATION_ID_MAP.put(o.getShapeID(), o);
    }


    private State currentState = new IdleState();

    public GUI(){
        this(new ArrayList<GraphicalObject>());
    }

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
        toolBar.repaint();
        canvas.repaint();
    }

    public int getToolBarHeight() {
        return 57;//toolBar.getHeight();
    }

    public class ToolBar extends JToolBar {

        HashMap<String, JButton> buttonCreatorsMap = new HashMap<>();

        public ToolBar() {
            {
                JButton button = new JButton("Učitaj");
                add(button);
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser c = new JFileChooser();
                        int rVal = c.showOpenDialog(GUI.this);
                        if (rVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                loadFile(Paths.get(c.getCurrentDirectory().toString(),c.getSelectedFile().getName()).toString());
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Unable to load file");
                            }
                        }
                    }
                });
            }
            {
                JButton button = new JButton("Pohrani");
                add(button);
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser c = new JFileChooser();
                        int rVal = c.showOpenDialog(GUI.this);
                        if (rVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                saveFile(Paths.get(c.getCurrentDirectory().toString(),c.getSelectedFile().getName()).toString());
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Unable to save file");
                            }
                        }
                    }
                });
            }
            {
                JButton button = new JButton("SVG Export");
                add(button);
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser c = new JFileChooser();
                        int rVal = c.showOpenDialog(GUI.this);
                        if (rVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                SVGexport(Paths.get(c.getCurrentDirectory().toString(), c.getSelectedFile().getName()).toString());
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Unable to export file");
                            }
                        }
                    }
                });
            }
            for (final GraphicalObject go : IMPLEMENTED_OBJECTS) {
                JButton button = new JButton(go.getShapeName());
                add(button);
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        GUI.this.currentState = new AddShapeState(documentModel, go);
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
                        GUI.this.currentState.onLeaving();
                        GUI.this.currentState = new SelectShapeState(documentModel);
                    }
                });
            }
            {
                JButton button = new JButton("Brisalo");
                add(button);
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        GUI.this.currentState.onLeaving();
                        GUI.this.currentState = new EraserState(documentModel);
                    }
                });
            }
        }
    }

    private void SVGexport(String path) throws IOException {
        SVGRendererImpl r = new SVGRendererImpl(path);
        for (GraphicalObject o : documentModel.getAllObjects())
            o.render(r);
        r.close();
    }

    private void saveFile(String path) throws IOException{
        List<String> lines = new ArrayList<>();
        for (GraphicalObject o: documentModel.getAllObjects())
            o.save(lines);
        Files.write(Paths.get(path), lines, Charset.defaultCharset());
    }

    private void loadFile(String path) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
        Stack<GraphicalObject> stack = new Stack<>();

        for (String line:lines)
        {
            String[] parts =line.split(" ",2);
            SERIALIZATION_ID_MAP.get(parts[0]).load(stack,parts[1]);
        }

        documentModel.setGraphicalObjects(stack);
    }
}
