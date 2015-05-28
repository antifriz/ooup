package hr.fer.zemris.ooup.lab3.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ivan on 5/28/15.
 */
public class FunkyComponent extends JComponent  {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.drawLine(100, 100, 100, 200);
        g.drawLine(100, 100, 200, 100);
        g.setColor(Color.BLACK);
        g.drawString("Prvi tekst", 200, 200);
        g.drawString("Drugi tekst", 250, 250);
    }

}
