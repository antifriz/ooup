package plugins;

import hr.fer.zemris.ooup.lab3.funkyeditor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.undomanager.UndoManager;
import hr.fer.zemris.ooup.lab3.funkyeditor.plugins.Plugin;

import javax.swing.*;
import java.util.Iterator;

/**
 * Created by ivan on 5/30/15.
 */
public class Statistika implements Plugin {
    @Override
    public String getName() {
        return "Statistika";
    }

    @Override
    public String getDescription() {
        return "plugin koji broji koliko ima redaka, riječi i slova u dokumentu i to prikazuje korisniku u dijalogu";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        Iterator<StringBuffer> it = model.allLines();

        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        while (it.hasNext()) {
            StringBuffer sb = it.next();
            lineCount++;
            boolean spacingBefore = true;
            for (int i = 0; i < sb.length(); i++) {
                charCount ++;
                if (!Character.isLetterOrDigit(sb.charAt(i))) {
                    spacingBefore = true;
                } else if (spacingBefore) {
                    wordCount++;
                    spacingBefore = false;
                }
            }
        }
        JOptionPane.showMessageDialog(null, String.format("Line count: %d\nWord count: %d\nCharacter count: %d\n",lineCount,wordCount,charCount));
    }
}
