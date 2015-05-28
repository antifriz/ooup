package hr.fer.zemris.ooup.lab3.components.texteditor;

import hr.fer.zemris.ooup.lab3.helpers.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

/**
 * Created by ivan on 5/28/15.
 */
public class TextEditor extends JComponent implements KeyListener {
    private TextEditorModel textEditorModel;

    protected static final int  CURSOR_THICKNESS = 0;

    public TextEditorModel getTextEditorModel() {
        return textEditorModel;
    }

    public TextEditor(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;

        textEditorModel.attachCursorObserver(new CursorObserver() {
            @Override
            public void updateCursorLocation(Location loc) {
                TextEditor.this.repaint();
            }
        });
    }


    @Override
    protected void processKeyEvent(KeyEvent e) {
        System.out.println("EUREKA");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintText(g);
        paintCursor(g);
    }

    protected void paintText(Graphics g){
        g.setColor(Color.BLACK);
        int yOffset = 0;
        Iterator<String> it = getTextEditorModel().allLines();
        while (it.hasNext()){
            yOffset+=g.getFontMetrics().getHeight();
            g.drawString(it.next(),0,yOffset);
        }
    }

    protected void paintCursor(Graphics g){
        Location cursorLocation = getTextEditorModel().getCursorLocation();
        int textHeight = g.getFontMetrics().getHeight();

        char[] line = getTextEditorModel().getLines().get(cursorLocation.getY()).toCharArray();
        int screenX = g.getFontMetrics().charsWidth(line,0,cursorLocation.getX());
        int screenY = textHeight *cursorLocation.getY();
        g.setColor(Color.RED);
        g.drawRect(screenX, screenY, CURSOR_THICKNESS, textHeight);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                getTextEditorModel().moveCursorUp();
                break;
            case KeyEvent.VK_DOWN:
                getTextEditorModel().moveCursorDown();
                break;
            case KeyEvent.VK_LEFT:
                getTextEditorModel().moveCursorLeft();
                break;
            case KeyEvent.VK_RIGHT:
                getTextEditorModel().moveCursorRight();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
