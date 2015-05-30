package hr.fer.zemris.ooup.lab3.components.menubar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ivan on 5/30/15.
 */
public class FunkyMenuBar extends JMenuBar {
    public FunkyMenuBar() {
        JMenu menu;
        JMenuItem menuItem;


        menu = new JMenu("File");
        add(menu);
        {
            menuItem = new JMenuItem("Open");
            menu.add(menuItem);
            {
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser c = new JFileChooser();
                        int rVal = c.showOpenDialog(FunkyMenuBar.this);
                        if (rVal == JFileChooser.APPROVE_OPTION) {
                            System.out.println(c.getSelectedFile().getName());
                            System.out.println(c.getCurrentDirectory().toString());
                        }
                        if (rVal == JFileChooser.CANCEL_OPTION) {
                            System.out.println("You pressed cancel");
                        }
                    }
                });
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
        add(menu);
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
        add(menu);
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
    }
}
