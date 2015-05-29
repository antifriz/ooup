package hr.fer.zemris.ooup.lab3.components.texteditor;

import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by ivan on 5/29/15.
 */
public class ClipboardStack {
    private Stack<String> stack;
    private List<ClipboardObserver> observers;

    public ClipboardStack() {
        this.stack = new Stack<String>();
        this.observers = new ArrayList<ClipboardObserver>();
    }

    public void attach(ClipboardObserver observer){
        this.observers.add(observer);
    }

    public void detach(ClipboardObserver observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(){
        for(ClipboardObserver observer: observers){
            observer.updateClipboard();
        }
    }

    public void push(String string){
        this.stack.push(string);
    }
    public String pop(){
        return this.stack.pop();
    }
    public String peek(){
        return this.stack.peek();
    }
}
