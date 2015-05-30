package plugins;

import hr.fer.zemris.ooup.lab3.funkyeditor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.undomanager.UndoManager;
import hr.fer.zemris.ooup.lab3.funkyeditor.plugins.Plugin;

import java.util.Iterator;

/**
 * Created by ivan on 5/30/15.
 */
public class VelikoSlovo implements Plugin {
    @Override
    public String getName() {
        return "VelikoSlovo";
    }

    @Override
    public String getDescription() {
        return "prolazi kroz dokument i svako prvo slovo riječi mijenja u veliko (\"ovo je tekst\" ==> \"Ovo Je Tekst\").";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        Iterator<StringBuffer> it = model.allLines();
        while(it.hasNext()){
            StringBuffer sb = it.next();
            boolean spacingBefore = true;
            for (int i = 0;i<sb.length();i++)
            {
                char c = sb.charAt(i);
                if (!Character.isLetterOrDigit(c)) spacingBefore = true;
                else if (spacingBefore) {
                    sb.setCharAt(i, Character.toUpperCase(c));
                    spacingBefore = false;
                }
            }
        }
        model.notifyTextObservers();
    }
}
