package hr.fer.zemris.ooup.lab3.frames;

import hr.fer.zemris.ooup.lab3.components.statusbar.FunkyStatusBar;
import hr.fer.zemris.ooup.lab3.components.texteditor.TextEditor;
import hr.fer.zemris.ooup.lab3.model.TextEditorModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ivan on 5/28/15.
 */
public class FunkyFrame extends JFrame {
    TextEditorModel model;
    String fileName;
    String dirName;

    public FunkyFrame() throws HeadlessException {
        fileName = "untitled.txt";
        dirName = System.getProperty("user.dir");
        model = new TextEditorModel();
    }

    public void initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(createToolBar(),BorderLayout.PAGE_START);

        TextEditor textEditor = new TextEditor(model);
        panel.add(textEditor, BorderLayout.CENTER);
        addKeyListener(textEditor);

        panel.add(createStatusBar(),BorderLayout.PAGE_END);
        add(panel);

        setJMenuBar(createMenuBar());

        model.notifyCursorObservers();
        model.notifyTextObservers();

        setTitle(String.format("File - %s",fileName));
    }


    private void loadFile(String fileName, String dirName) throws IOException {
        model.setLines(new String( Files.readAllBytes(Paths.get(dirName,fileName))));
        setTitle(String.format("File - %s",fileName));
    }
    private void saveFile(String fileName, String dirName) throws IOException {
        File dir = new File(dirName);
        File file = new File(dir,fileName);
        if(!file.exists())
            file.createNewFile();

        FileWriter fw = new FileWriter(file.getAbsoluteFile());

        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(model.getLinesString());
        bw.close();
    }



    private JToolBar createToolBar() {
        return new JToolBar();
    }

    private JPanel createStatusBar() {
        return new FunkyStatusBar(model);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;


        menuBar= new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);
        {
            menuItem = new JMenuItem("Open");
            menu.add(menuItem);
            {
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser c = new JFileChooser();
                        int rVal = c.showOpenDialog(FunkyFrame.this);
                        if (rVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                loadFile(c.getSelectedFile().getName(), c.getCurrentDirectory().toString());
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Unable to load file");
                            }
                        }
                    }
                });
            }

            menuItem = new JMenuItem("Save");
            menu.add(menuItem);
            {
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser c = new JFileChooser();
                        int rVal = c.showSaveDialog(FunkyFrame.this);
                        if (rVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                saveFile(c.getSelectedFile().getName(), c.getCurrentDirectory().toString());
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Unable to save file");
                            }
                        }
                    }
                });
            }

            menuItem = new JMenuItem("Exit");
            menu.add(menuItem);
            {

            }
        }

        menu = new JMenu("Edit");
        menuBar.add(menu);
        {
            menuItem = new JMenuItem("Undo");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Redo");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Cut");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Copy");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Paste");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Paste and Take");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Delete selection");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Clear document");
            menu.add(menuItem);
            {

            }
        }

        menu = new JMenu("Move");
        menuBar.add(menu);
        {
            menuItem = new JMenuItem("Cursor to document start");
            menu.add(menuItem);
            {

            }

            menuItem = new JMenuItem("Cursor to document end");
            menu.add(menuItem);
            {

            }
        }
        return menuBar;
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
            System.exit(0);
        }
    }
}
