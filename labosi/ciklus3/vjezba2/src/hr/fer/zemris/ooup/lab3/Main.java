/**
 * Created by ivan on 5/28/15.
 */

package hr.fer.zemris.ooup.lab3;

import hr.fer.zemris.ooup.lab3.components.texteditor.TextEditor;
import hr.fer.zemris.ooup.lab3.components.texteditor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.frames.FunkyFrame;
import hr.fer.zemris.ooup.lab3.helpers.Location;
import hr.fer.zemris.ooup.lab3.helpers.LocationRange;

import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FunkyFrame frame = new FunkyFrame();
                    frame.setBounds(100, 100, 500, 400);
                    frame.setVisible(true);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



}
