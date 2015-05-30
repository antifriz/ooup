package hr.fer.zemris.ooup.lab3.model;

import java.util.Stack;

/**
 * Created by ivan on 5/29/15.
 */
public class UndoManager {

    private static UndoManager instance=new UndoManager();

    private UndoManager(){}

    Stack<EditAction> undoStack = new Stack<EditAction>();
    Stack<EditAction> redoStack = new Stack<EditAction>();

    public static UndoManager getInstance() {
        return instance;
    }

    public void undo(){
        if(undoStack.isEmpty()) return;
        EditAction ea = undoStack.pop();
        ea.executeUndo();
        redoStack.push(ea);
    }

    public void redo(){
        if(redoStack.isEmpty()) return;
        EditAction ea = redoStack.pop();
        ea.executeDo();
        undoStack.push(ea);
    }

    public void push(EditAction ea){
        undoStack.push(ea);
        redoStack.clear();
    }



}
