package hr.fer.zemris.ooup.lab3.frames;

import hr.fer.zemris.ooup.lab3.components.statusbar.FunkyStatusBar;
import hr.fer.zemris.ooup.lab3.components.texteditor.TextEditor;
import hr.fer.zemris.ooup.lab3.components.texteditor.TextEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by ivan on 5/28/15.
 */
public class FunkyFrame extends JFrame {
    TextEditorModel model = new TextEditorModel("Burek s mesom.\nJos jedan burek s mesom.\nGle, sirnica!");

    public FunkyFrame() throws HeadlessException {
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

            }

            menuItem = new JMenuItem("Save");
            menu.add(menuItem);
            {

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
