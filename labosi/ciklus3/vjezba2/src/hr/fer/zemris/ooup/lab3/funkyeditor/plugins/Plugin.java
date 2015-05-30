package hr.fer.zemris.ooup.lab3.funkyeditor.plugins;

import hr.fer.zemris.ooup.lab3.funkyeditor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.undomanager.UndoManager;

/**
 * Created by ivan on 5/30/15.
 */
public interface Plugin {
    String getName(); // ime plugina (za izbornicku stavku)
    String getDescription(); // kratki opis
    void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack);
}
