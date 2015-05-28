package hr.fer.zemris.ooup.lab3.components.texteditor;

import hr.fer.zemris.ooup.lab3.helpers.Location;
import hr.fer.zemris.ooup.lab3.helpers.LocationRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ivan on 5/28/15.
 */
public class TextEditorModel {

    private List<String> lines;
    private LocationRange selectionRange;
    private Location cursorLocation;

    private List<CursorObserver> observers;

    public TextEditorModel(String lines) {
        this.lines = Arrays.asList(lines.split(System.lineSeparator()));
        this.cursorLocation = new Location(0, 0);
        this.selectionRange = null;
        this.observers = new ArrayList<CursorObserver>();
    }

    public void attachCursorObserver(CursorObserver observer){
        observers.add(observer);
    }

    public void detachCursorObserver(CursorObserver observer){
        observers.remove(observer);
    }

    public void notifyCursorObservers(){
        for(CursorObserver observer : observers)
            observer.updateCursorLocation(cursorLocation);
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }

    public void setCursorLocation(Location cursorLocation) {
        this.cursorLocation = cursorLocation;
    }

    public void moveCursorUp(){
        int y = cursorLocation.getY();
        int x = cursorLocation.getX();
        if(y >0)
        {
            cursorLocation.setY(y-1);
            cursorLocation.setX(Math.min(x,lines.get(cursorLocation.getY()).length()));
            notifyCursorObservers();
        }
    }
    public void moveCursorDown(){

        int y = cursorLocation.getY();
        int x = cursorLocation.getX();
        if(y +1<lines.size())
        {
            cursorLocation.setY(y + 1);
            cursorLocation.setX(Math.min(x,lines.get(cursorLocation.getY()).length()));
            notifyCursorObservers();
        }
    }
    public void moveCursorLeft(){

        int x = cursorLocation.getX();
        if(x>0)
        {
            cursorLocation.setX(x - 1);
            notifyCursorObservers();
        }
    }
    public void moveCursorRight(){

        int x = cursorLocation.getX();
        if(x <lines.get(cursorLocation.getY()).length())
        {
            cursorLocation.setX(x + 1);
            notifyCursorObservers();
        }
    }

    public Iterator<String> allLines() {
        return new Iterator<String>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return lines.size() != i;
            }

            @Override
            public String next() {
                return lines.get(i++);
            }

            @Override
            public void remove() {
                assert false;
            }
        };
    }

    public Iterator<String> linesRange(final int index1, final int index2) {
        return new Iterator<String>() {
            int i = index1;

            @Override
            public boolean hasNext() {
                return i != index2 && i != lines.size();
            }

            @Override
            public String next() {
                return lines.get(i++);
            }

            @Override
            public void remove() {
                assert false;
            }
        };
    }

    public List<String> getLines() {
        return lines;
    }
}
