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

        panel.add(new FunkyToolBar(), BorderLayout.PAGE_START);

        TextEditor textEditor = new TextEditor(model);
        addKeyListener(textEditor);

        panel.add(textEditor, BorderLayout.CENTER);


        panel.add(createBorderPanel(), BorderLayout.WEST);

        panel.add(new FunkyStatusBar(model), BorderLayout.PAGE_END);
        add(panel);

        setJMenuBar(createMenuBar());

        setTitle(String.format("File - %s", fileName));

        model.notifyCursorObservers();
        model.notifyTextObservers();
        model.getClipboardStack().notifyObservers();
        UndoManager.getInstance().notifyObservers();
    }

    private JPanel createBorderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        return panel;
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

    class FunkyToolBar extends JToolBar implements UndoManagerObserver, ClipboardObserver, TextObserver {
        JButton itemUndo, itemRedo, itemCopy, itemPaste, itemCut;

        public FunkyToolBar() {
            model.attachTextObserver(this);
            model.getClipboardStack().attach(this);
            UndoManager.getInstance().attach(this);

            itemUndo = new JButton(new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Undo16.gif")));
            add(itemUndo);
            {
                itemUndo.setFocusable(false);
                itemUndo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        UndoManager.getInstance().undo();
                    }
                });
            }

            itemRedo = new JButton(new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Redo16.gif")));
            add(itemRedo);
            {
                itemRedo.setFocusable(false);
                itemRedo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        UndoManager.getInstance().redo();
                    }
                });
            }

            itemCut = new JButton(new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Redo16.gif")));
            add(itemCut);
            {
                itemCut.setFocusable(false);
                itemCut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        model.cutSelection();
                    }
                });
            }

            itemCopy = new JButton(new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Copy16.gif")));
            add(itemCopy);
            {
                itemCopy.setFocusable(false);
                itemCopy.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        model.copySelection();
                    }
                });
            }

            itemPaste = new JButton(new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Paste16.gif")));
            add(itemPaste);
            {
                itemPaste.setFocusable(false);
                itemPaste.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        model.paste();
                    }
                });
            }

            setFloatable(false);

        }

        @Override
        public void onUpdateClipboard() {
            itemPaste.setEnabled(!model.getClipboardStack().isEmpty());
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
            repaint();
        }
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
                menuItem = new JMenuItem("Open", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Open16.gif")));
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

                menuItem = new JMenuItem("Save", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Save16.gif")));
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

                menuItem = new JMenuItem("Exit", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Stop16.gif")));
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
                itemUndo = new JMenuItem("Undo", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Undo16.gif")));
                menu.add(itemUndo);
                {
                    itemUndo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            UndoManager.getInstance().undo();
                        }
                    });
                }

                itemRedo = new JMenuItem("Redo", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Redo16.gif")));
                menu.add(itemRedo);
                {
                    itemRedo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            UndoManager.getInstance().redo();
                        }
                    });
                }

                itemCut = new JMenuItem("Cut", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Cut16.gif")));
                menu.add(itemCut);
                {
                    itemCut.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.cutSelection();
                        }
                    });
                }

                itemCopy = new JMenuItem("Copy", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Copy16.gif")));
                menu.add(itemCopy);
                {
                    itemCopy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.copySelection();
                        }
                    });
                }

                itemPaste = new JMenuItem("Paste", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Paste16.gif")));
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

                itemDeleteSection = new JMenuItem("Delete selection", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/Delete16.gif")));
                menu.add(itemDeleteSection);
                {
                    itemDeleteSection.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.deleteSelection();
                        }
                    });
                }

                menuItem = new JMenuItem("Clear document", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/general/New16.gif")));
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
                menuItem = new JMenuItem("Cursor to document start", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/navigation/Back16.gif")));
                menu.add(menuItem);
                {
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.moveCursorStart();
                        }
                    });
                }

                menuItem = new JMenuItem("Cursor to document end", new ImageIcon(getClass().getResource("/jlfgr-1_0/toolbarButtonGraphics/navigation/Forward16.gif")));
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
                        System.out.println(String.format("Plugin \"%s\" couldn't be loaded", pluginName));
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
