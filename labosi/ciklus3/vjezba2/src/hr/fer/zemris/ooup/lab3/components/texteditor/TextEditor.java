package hr.fer.zemris.ooup.lab3.components.texteditor;

import hr.fer.zemris.ooup.lab3.helpers.Location;
import hr.fer.zemris.ooup.lab3.helpers.LocationRange;

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

    protected static final int CURSOR_THICKNESS = 0;
    protected static final Color TXT_COLOR = Color.BLACK;
    protected static final Color SELECTION_BG_COLOR = Color.getHSBColor(0.5f, 0.8f, 0.5f);
    protected static final Color SELECTION_TXT_COLOR = Color.BLACK;

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

        textEditorModel.attachTextObserver(new TextObserver() {
            @Override
            public void updateText() {
                TextEditor.this.repaint();
            }
        });


        //this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"pressed");


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

    protected void paintText(Graphics g) {
        int yOffset = 0;

        g.setColor(TXT_COLOR);

        int textHeight = g.getFontMetrics().getHeight();
        if (getTextEditorModel().isSelectedModeActive()) {

            LocationRange selection = getTextEditorModel().getSelectionRange();
            {
                Iterator<StringBuffer> it = getTextEditorModel().linesRange(0, selection.getLower().getY());

                while (it.hasNext()) {
                    yOffset += textHeight;
                    g.drawString(it.next().toString(), 0, yOffset);
                }
            }
            if (selection.isSingleLine()) {
                yOffset += textHeight;
                int lowerLimit = selection.getLower().getX();
                int upperLimit = selection.getHigher().getX();

                StringBuffer line = getTextEditorModel().getLines().get(selection.getLower().getY());

                paintLine(g, yOffset, lowerLimit, upperLimit, line);
            } else {
                {
                    yOffset += textHeight;

                    int lowerLimit = selection.getLower().getX();

                    StringBuffer line = getTextEditorModel().getLines().get(selection.getLower().getY());

                    paintLine(g, yOffset, lowerLimit, line.length(), line);
                }
                Iterator<StringBuffer> it = getTextEditorModel().linesRange(selection.getLower().getY() + 1, selection.getHigher().getY());

                while (it.hasNext()) {
                    yOffset += textHeight;
                    StringBuffer line = it.next();
                    paintLine(g, yOffset, 0, line.length(), line);
                }
                {
                    yOffset += textHeight;

                    int upperLimit = selection.getHigher().getX();

                    StringBuffer line = getTextEditorModel().getLines().get(selection.getHigher().getY());

                    paintLine(g, yOffset, 0, upperLimit, line);
                }
            }
            {
                Iterator<StringBuffer> it = getTextEditorModel().linesRange(selection.getHigher().getY() + 1, getTextEditorModel().getLines().size());

                while (it.hasNext()) {
                    yOffset += textHeight;
                    g.drawString(it.next().toString(), 0, yOffset);
                }
            }

        } else {
            Iterator<StringBuffer> it = getTextEditorModel().allLines();
            while (it.hasNext()) {
                yOffset += textHeight;
                String str = it.next().toString();
                g.drawString(str, 0, yOffset);
            }
        }
    }

    private void paintLine(Graphics g, int yOffset, int lowerLimit, int upperLimit, StringBuffer line) {
        int textHeight = g.getFontMetrics().getHeight();

        g.drawString(line.substring(0, lowerLimit), 0, yOffset);

        int pixelsBefore = g.getFontMetrics().charsWidth(line.toString().toCharArray(), 0, lowerLimit);
        int pixelsSelection = g.getFontMetrics().charsWidth(line.toString().toCharArray(), lowerLimit, upperLimit - lowerLimit);
        g.setColor(SELECTION_BG_COLOR);
        g.fillRect(pixelsBefore, yOffset - textHeight, pixelsSelection, textHeight+g.getFontMetrics().getMaxDescent());

        g.setColor(SELECTION_TXT_COLOR);
        g.drawString(line.substring(lowerLimit, upperLimit), pixelsBefore, yOffset);

        g.setColor(TXT_COLOR);
        g.drawString(line.substring(upperLimit, line.length()), pixelsBefore + pixelsSelection, yOffset);
    }

    protected void paintCursor(Graphics g) {
        Location cursorLocation = getTextEditorModel().getCursorLocation();
        int textHeight = g.getFontMetrics().getHeight();

        char[] line = getTextEditorModel().getLines().get(cursorLocation.getY()).toString().toCharArray();
        int screenX = g.getFontMetrics().charsWidth(line, 0, cursorLocation.getX());
        int screenY = textHeight * cursorLocation.getY();
        g.setColor(Color.RED);
        g.drawRect(screenX, screenY, CURSOR_THICKNESS, textHeight+g.getFontMetrics().getMaxDescent());
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Location lastCursorLocation = getTextEditorModel().getCursorLocation();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                getTextEditorModel().moveCursorUp(e.isShiftDown());
                break;
            case KeyEvent.VK_DOWN:
                getTextEditorModel().moveCursorDown(e.isShiftDown());
                break;
            case KeyEvent.VK_LEFT:
                getTextEditorModel().moveCursorLeft(e.isShiftDown());
                break;
            case KeyEvent.VK_RIGHT:
                getTextEditorModel().moveCursorRight(e.isShiftDown());
                break;
            case KeyEvent.VK_BACK_SPACE:
                if(getTextEditorModel().isSelectedModeActive())
                    getTextEditorModel().deleteRange(getTextEditorModel().getSelectionRange());
                else
                    getTextEditorModel().deleteBefore();
                break;
            case KeyEvent.VK_DELETE:
                if(getTextEditorModel().isSelectedModeActive())
                    getTextEditorModel().deleteRange(getTextEditorModel().getSelectionRange());
                else
                    getTextEditorModel().deleteAfter();
                break;
            default:
                break;
        }
/*        if (getTextEditorModel().isSelectedModeActive()) {
            System.out.println(getTextEditorModel().getSelectionRange().getLower());
            System.out.println(getTextEditorModel().getSelectionRange().getHigher());
            System.out.println();
        }*/
    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
