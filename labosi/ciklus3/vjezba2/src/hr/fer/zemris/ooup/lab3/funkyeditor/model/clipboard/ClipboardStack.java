package hr.fer.zemris.ooup.lab3.funkyeditor.model.clipboard;

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
            observer.onUpdateClipboard();
        }
    }

    public void push(String string){
        this.stack.push(string);
        notifyObservers();
    }
    public String pop(){
        assert !this.stack.isEmpty();
        String str = this.stack.pop();
        notifyObservers();
        return str;
    }
    public String peek(){
        assert !this.stack.isEmpty();
        String str = this.stack.peek();
        notifyObservers();
        return str;
    }

    public boolean isEmpty(){
        return this.stack.isEmpty();
    }
}
