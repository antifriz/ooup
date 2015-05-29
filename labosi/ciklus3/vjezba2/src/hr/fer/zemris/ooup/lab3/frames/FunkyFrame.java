package hr.fer.zemris.ooup.lab3.frames;

import hr.fer.zemris.ooup.lab3.components.texteditor.TextEditor;
import hr.fer.zemris.ooup.lab3.components.texteditor.TextEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by ivan on 5/28/15.
 */
public class FunkyFrame extends JFrame {
    public FunkyFrame() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TextEditorModel tem = new TextEditorModel("Burek s mesom.\nJos jedan burek s mesom.\nGle, sirnica!");

        TextEditor textEditor = new TextEditor(tem);
        add(textEditor);
        addKeyListener(textEditor);
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
