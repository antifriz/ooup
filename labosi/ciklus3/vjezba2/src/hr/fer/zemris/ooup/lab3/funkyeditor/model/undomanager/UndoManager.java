package hr.fer.zemris.ooup.lab3.funkyeditor.model.undomanager;

import hr.fer.zemris.ooup.lab3.funkyeditor.model.EditAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by ivan on 5/29/15.
 */
public class UndoManager {

    private static UndoManager instance=new UndoManager();

    private UndoManager(){}

    Stack<EditAction> undoStack = new Stack<EditAction>();
    Stack<EditAction> redoStack = new Stack<EditAction>();
    List<UndoManagerObserver> observers = new ArrayList<UndoManagerObserver>();

    public static UndoManager getInstance() {
        return instance;
    }

    public void attach(UndoManagerObserver observer){
        observers.add(observer);
    }
    public void detach(UndoManagerObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(UndoManagerObserver observer:observers)
            observer.onUpdateUndoManager();
    }


    public void undo(){
        if(undoStack.isEmpty()) return;
        EditAction ea = undoStack.pop();
        ea.executeUndo();
        redoStack.push(ea);
        notifyObservers();
    }

    public void redo(){
        if(redoStack.isEmpty()) return;
        EditAction ea = redoStack.pop();
        ea.executeDo();
        undoStack.push(ea);
        notifyObservers();
    }

    public void push(EditAction ea){
        undoStack.push(ea);
        redoStack.clear();
        notifyObservers();
    }


    public boolean isEmptyUndo() {
        return undoStack.isEmpty();
    }
    public boolean isEmptyRedo() {
        return redoStack.isEmpty();
    }
}
