package hr.fer.zemris.ooup.lab3.funkyeditor.frame;

import hr.fer.zemris.ooup.lab3.funkyeditor.components.statusbar.FunkyStatusBar;
import hr.fer.zemris.ooup.lab3.funkyeditor.components.texteditor.TextEditor;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.TextObserver;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.clipboard.ClipboardObserver;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.undomanager.UndoManager;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.undomanager.UndoManagerObserver;
import hr.fer.zemris.ooup.lab3.funkyeditor.plugins.Plugin;
import hr.fer.zemris.ooup.lab3.funkyeditor.plugins.PluginFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by ivan on 5/28/15.
 */

public class FunkyFrame extends JFrame {
    TextEditorModel model;
    String fileName;
    String dirName;
    String pluginDir;

    public FunkyFrame() throws HeadlessException {
        fileName = "untitled.txt";
        dirName = System.getProperty("user.dir");
        pluginDir = Paths.get(System.getProperty("user.dir"), "out/production/vjezba2/plugins").toString();
        model = new TextEditorModel();
    }

    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(createToolBar(), BorderLayout.PAGE_START);

        TextEditor textEditor = new TextEditor(model);
        panel.add(textEditor, BorderLayout.CENTER);
        addKeyListener(textEditor);

        panel.add(createStatusBar(), BorderLayout.PAGE_END);
        add(panel);

        setJMenuBar(createMenuBar());

        model.notifyCursorObservers();
        model.notifyTextObservers();
        model.getClipboardStack().notifyObservers();
        UndoManager.getInstance().notifyObservers();

        setTitle(String.format("File - %s", fileName));
    }


    private void loadFile(String fileName, String dirName) throws IOException {
        model.setLines(new String(Files.readAllBytes(Paths.get(dirName, fileName))));
        setTitle(String.format("File - %s", fileName));
    }

    private void saveFile(String fileName, String dirName) throws IOException {
        File dir = new File(dirName);
        File file = new File(dir, fileName);
        if (!file.exists())
            file.createNewFile();

        FileWriter fw = new FileWriter(file.getAbsoluteFile());

        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(model.getLinesString());
        bw.close();
    }


    private JToolBar createToolBar() {
        return new JToolBar();
    }

    private JPanel createStatusBar() {
        return new FunkyStatusBar(model);
    }

    class FunkyMenuBar extends JMenuBar implements UndoManagerObserver, ClipboardObserver, TextObserver {
        JMenuItem itemUndo, itemRedo, itemCopy, itemPaste, itemPasteSpecial, itemCut, itemDeleteSection;

        public FunkyMenuBar() {
            model.attachTextObserver(this);
            model.getClipboardStack().attach(this);
            UndoManager.getInstance().attach(this);

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
                            int rVal = c.showOpenDialog(FunkyFrame.this);
                            if (rVal == JFileChooser.APPROVE_OPTION) {
                                try {
                                    loadFile(c.getSelectedFile().getName(), c.getCurrentDirectory().toString());
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, "Unable to load file");
                                }
                            }
                        }
                    });
                }

                menuItem = new JMenuItem("Save");
                menu.add(menuItem);
                {
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            JFileChooser c = new JFileChooser();
                            int rVal = c.showSaveDialog(FunkyFrame.this);
                            if (rVal == JFileChooser.APPROVE_OPTION) {
                                try {
                                    saveFile(c.getSelectedFile().getName(), c.getCurrentDirectory().toString());
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, "Unable to save file");
                                }
                            }
                        }
                    });
                }

                menuItem = new JMenuItem("Exit");
                menu.add(menuItem);
                {
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            FunkyFrame.this.dispose();
                            System.exit(0);
                        }
                    });
                }
            }

            menu = new JMenu("Edit");
            add(menu);
            {
                itemUndo = new JMenuItem("Undo");
                menu.add(itemUndo);
                {
                    itemUndo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            UndoManager.getInstance().undo();
                        }
                    });
                }

                itemRedo = new JMenuItem("Redo");
                menu.add(itemRedo);
                {
                    itemRedo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            UndoManager.getInstance().redo();
                        }
                    });
                }

                itemCut = new JMenuItem("Cut");
                menu.add(itemCut);
                {
                    itemCut.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.cutSelection();
                        }
                    });
                }

                itemCopy = new JMenuItem("Copy");
                menu.add(itemCopy);
                {
                    itemCopy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.copySelection();
                        }
                    });
                }

                itemPaste = new JMenuItem("Paste");
                menu.add(itemPaste);
                {
                    itemPaste.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.paste();
                        }
                    });
                }

                itemPasteSpecial = new JMenuItem("Paste and Take");
                menu.add(itemPasteSpecial);
                {
                    itemPasteSpecial.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.pasteSpecial();
                        }
                    });
                }

                itemDeleteSection = new JMenuItem("Delete selection");
                menu.add(itemDeleteSection);
                {
                    itemDeleteSection.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.deleteSelection();
                        }
                    });
                }

                menuItem = new JMenuItem("Clear document");
                menu.add(menuItem);
                {
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.setLines("");
                        }
                    });
                }
            }

            menu = new JMenu("Move");
            add(menu);
            {
                menuItem = new JMenuItem("Cursor to document start");
                menu.add(menuItem);
                {
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.moveCursorStart();
                        }
                    });
                }

                menuItem = new JMenuItem("Cursor to document end");
                menu.add(menuItem);
                {
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.moveCursorEnd();
                        }
                    });
                }
            }
            loadPlugins();
        }

        void loadPlugins() {
            JMenu menu = new JMenu("Plugins");
            System.out.println(pluginDir);
            final File dir = new File(pluginDir);
            try {

                for (final File fileEntry : dir.listFiles()) {
                    if (!fileEntry.getName().endsWith(".class")) continue;
                    String pluginName = fileEntry.getName().replace(".class", "");
                   try {


                        final Plugin plugin;
                        plugin = PluginFactory.newInstance(pluginName);

                    JMenuItem item = new JMenuItem(plugin.getName());
                        menu.add(item);
                        item.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                plugin.execute(model, UndoManager.getInstance(), model.getClipboardStack());
                            }
                        });
                     } catch (Exception e) {
                        System.out.println(String.format("Plugin \"%s\" couldn't be loaded",pluginName));
                    }
                }
            } catch (Exception e) {
                System.out.println(String.format("Can't access plugin directory"));
            }
            add(menu);
        }

        @Override
        public void onUpdateClipboard() {
            itemPaste.setEnabled(!model.getClipboardStack().isEmpty());
            itemPasteSpecial.setEnabled(!model.getClipboardStack().isEmpty());
            repaint();
        }

        @Override
        public void onUpdateUndoManager() {
            itemUndo.setEnabled(!UndoManager.getInstance().isEmptyUndo());
            itemRedo.setEnabled(!UndoManager.getInstance().isEmptyRedo());
            repaint();
        }

        @Override
        public void onUpdateTextObserver() {
            itemCopy.setEnabled(model.isSelectedModeActive());
            itemCut.setEnabled(model.isSelectedModeActive());
            itemDeleteSection.setEnabled(model.isSelectedModeActive());
            repaint();
        }
    }

    private JMenuBar createMenuBar() {

        return new FunkyMenuBar();
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
            System.exit(0);
        }
    }
}
