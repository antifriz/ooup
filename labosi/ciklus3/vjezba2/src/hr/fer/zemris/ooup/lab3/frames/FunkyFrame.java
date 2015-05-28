package hr.fer.zemris.ooup.lab3.frames;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by ivan on 5/28/15.
 */
public class FunkyFrame extends JFrame {
    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            this.dispose();
            System.exit(0);
        }
    }
}
