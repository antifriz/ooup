/**
 * Created by ivan on 5/28/15.
 */

package hr.fer.zemris.ooup.lab3;

import hr.fer.zemris.ooup.lab3.funkyeditor.frame.FunkyFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FunkyFrame frame = new FunkyFrame();
                    frame.setBounds(100, 100, 500, 400);
                    frame.setVisible(true);
                    frame.initialize();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



}
