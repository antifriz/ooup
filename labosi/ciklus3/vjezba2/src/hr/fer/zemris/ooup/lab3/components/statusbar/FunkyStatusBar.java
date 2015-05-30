package hr.fer.zemris.ooup.lab3.components.statusbar;

import hr.fer.zemris.ooup.lab3.model.CursorObserver;
import hr.fer.zemris.ooup.lab3.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.model.TextObserver;
import hr.fer.zemris.ooup.lab3.helpers.Location;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by ivan on 5/29/15.
 */
public class FunkyStatusBar extends JPanel implements CursorObserver,TextObserver{
    TextEditorModel model;
    JLabel cursorLocationLabel;
    JLabel lineCounterLabel;

    public FunkyStatusBar(TextEditorModel model) {
        this.model = model;

        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setPreferredSize(new Dimension(getWidth(), 16));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        cursorLocationLabel = new JLabel();
        cursorLocationLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        add(cursorLocationLabel);

        lineCounterLabel = new JLabel();
        lineCounterLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        add(lineCounterLabel);

        model.attachTextObserver(this);
        model.attachCursorObserver(this);
    }

    @Override
    public void updateCursorLocation(Location loc) {
        cursorLocationLabel.setText(String.format("Cursor: %s",loc.toString()));
    }

    @Override
    public void updateText() {
        lineCounterLabel.setText(String.format("Lines: %d",model.getLines().size()));
    }
}
