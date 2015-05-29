package hr.fer.zemris.ooup.lab3.components.texteditor;

import hr.fer.zemris.ooup.lab3.helpers.Location;
import hr.fer.zemris.ooup.lab3.helpers.LocationRange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ivan on 5/28/15.
 */
public class TextEditorModel {

    private List<StringBuffer> lines;
    private LocationRange selectionRange;
    private Location cursorLocation;

    private List<CursorObserver> cursorObservers;
    private List<TextObserver> textObservers;


    public TextEditorModel(String lines) {
        this.lines = new ArrayList<StringBuffer>();

        for (String line : lines.split(System.lineSeparator()))
            this.lines.add(new StringBuffer(line));

        this.cursorLocation = new Location(0, 0);
        this.selectionRange = null;
        this.cursorObservers = new ArrayList<CursorObserver>();
        this.textObservers = new ArrayList<TextObserver>();
    }

    public int lineLength(int i) {
        assert i < lines.size();
        return lines.get(i).length();
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }

    public void setCursorLocation(Location cursorLocation) {
        this.cursorLocation = cursorLocation;
        notifyCursorObservers();
    }

    public void attachCursorObserver(CursorObserver observer) {
        cursorObservers.add(observer);
    }

    public void detachCursorObserver(CursorObserver observer) {
        cursorObservers.remove(observer);
    }

    public void notifyCursorObservers() {
        for (CursorObserver observer : cursorObservers)
            observer.updateCursorLocation(cursorLocation);
    }

    public void moveCursorUp(boolean selectMode) {
        if (cursorLocation.getY() == 0)
            return;
        Location lastLocation = cursorLocation.copy();

        cursorLocation.setY(cursorLocation.getY() - 1);
        cursorLocation.setX(Math.min(lineLength(cursorLocation.getY()), cursorLocation.getX()));

        processCursorMove(selectMode, lastLocation);
    }

    public void moveCursorDown(boolean selectMode) {
        if (cursorLocation.getY() + 1 >= lines.size())
            return;
        Location lastLocation = cursorLocation.copy();

        cursorLocation.incY();
        cursorLocation.setX(Math.min(lineLength(cursorLocation.getY()), cursorLocation.getX()));

        processCursorMove(selectMode, lastLocation);
    }

    public void moveCursorLeft(boolean selectMode) {
        Location lastLocation = cursorLocation.copy();

        if (cursorLocation.getX() == 0) {
            if (cursorLocation.getY() == 0)
                return;
            cursorLocation.decY();
            cursorLocation.setX(lineLength(cursorLocation.getY()));
        } else {
            cursorLocation.decX();
        }

        processCursorMove(selectMode, lastLocation);
    }

    public void moveCursorRight(boolean selectMode) {
        Location lastLocation = cursorLocation.copy();

        if (cursorLocation.getX() >= lineLength(cursorLocation.getY())) {
            if (cursorLocation.getY() + 1 >= lines.size())
                return;
            cursorLocation.incY();
            cursorLocation.setX(0);
        }
        cursorLocation.incX();

        processCursorMove(selectMode, lastLocation);
    }

    public void correctCursorAndNotify() {
        int hash = cursorLocation.hashCode();

        cursorLocation.setY(Math.max(cursorLocation.getY(), 0));
        cursorLocation.setY(Math.min(cursorLocation.getY(), lines.size() - 1));

        cursorLocation.setX(Math.max(cursorLocation.getX(), 0));
        cursorLocation.setX(Math.min(cursorLocation.getX(), lines.get(cursorLocation.getY()).length()));

        if (cursorLocation.hashCode() != hash)
            notifyCursorObservers();
    }

    public boolean isSelectedModeActive() {
        return selectionRange != null;
    }


    public LocationRange getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(LocationRange r) {
        selectionRange = r;
    }

    public void processCursorMove(boolean isSelectMode, Location last) {
        if (isSelectMode) {
            appendToSelection(last);
        } else {
            clearSelection();
        }

//
//        if(!from.lessThan(to)){
//            // nothing to select
//            clearSelection();
//            return;
//        }

        notifyTextObservers();
    }

    private void appendToSelection(Location last) {
        if (selectionRange == null)
            selectionRange = new LocationRange(last, cursorLocation.copy());
        else {
            selectionRange = new LocationRange(selectionRange.getFrom(), cursorLocation);
            if (selectionRange.getFrom().equals(selectionRange.getTo())) {
                clearSelection();
                return;
            }
        }
        notifyTextObservers();
    }

    public List<StringBuffer> getLines() {
        return lines;
    }

    public Iterator<StringBuffer> allLines() {
        return new Iterator<StringBuffer>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < lines.size();
            }

            @Override
            public StringBuffer next() {
                return lines.get(i++);
            }

            @Override
            public void remove() {
                assert false;
            }
        };
    }

    public Iterator<StringBuffer> linesRange(final int index1, final int index2) {
        return new Iterator<StringBuffer>() {
            int i = index1;

            @Override
            public boolean hasNext() {
                return i < index2 && i < lines.size();
            }

            @Override
            public StringBuffer next() {
                return lines.get(i++);
            }

            @Override
            public void remove() {
                lines.remove(i);
            }
        };
    }

    public void attachTextObserver(TextObserver observer) {
        textObservers.add(observer);
    }

    public void detachTextObserver(TextObserver observer) {
        textObservers.remove(observer);
    }

    public void notifyTextObservers() {
        for (TextObserver observer : textObservers)
            observer.updateText();
    }

    public void deleteBefore() {
        if (cursorLocation.getX() == 0 && cursorLocation.getY() == 0)
            return;

        Location lastLocation = cursorLocation.copy();
        moveCursorLeft(false);
        if (lastLocation.getX() == 0) {
            if (lastLocation.getY() == 0)
                return;
            lines.get(lastLocation.getY() - 1).append(lines.get(lastLocation.getY()));
            lines.remove(lastLocation.getY());
        } else {
            lines.get(lastLocation.getY()).deleteCharAt(lastLocation.getX() - 1);
        }
        notifyTextObservers();
    }

    public void deleteAfter() {
        if (cursorLocation.getX() == lineLength(cursorLocation.getY())) {
            if (cursorLocation.getY() == lines.size() - 1)
                return;
            lines.get(cursorLocation.getY()).append(lines.get(cursorLocation.getY() + 1));
            lines.remove(cursorLocation.getY() + 1);
        } else {
            lines.get(cursorLocation.getY()).deleteCharAt(cursorLocation.getX());
        }
        notifyTextObservers();
    }

    public void deleteRange(LocationRange r) {

        boolean oneLiner = r.getFrom().getY() == r.getTo().getY();

        setCursorLocation(r.getLower().copy());

        if (oneLiner) {
            lines.get(r.getLower().getY()).delete(r.getLower().getX(), r.getHigher().getX());
        } else {
            lines.get(r.getLower().getY()).delete(r.getLower().getX(), lines.get(r.getLower().getY()).length());

            Iterator<StringBuffer> it = linesRange(r.getLower().getY() + 1, r.getHigher().getY());
            while (it.hasNext())
                it.remove();

            lines.get(r.getLower().getY() + 1).delete(0, r.getHigher().getX());
            lines.get(r.getLower().getY()).append(lines.get(r.getLower().getY() + 1));
            lines.remove(r.getLower().getY() + 1);

        }
        clearSelection();

        notifyTextObservers();
    }

    public void deleteSelection(){
        if(isSelectedModeActive())
            deleteRange(selectionRange);
    }

    public void clearSelection() {
        if (selectionRange == null)
            return;
        selectionRange = null;
        notifyTextObservers();
    }

    public void insert(char c) {
        if (c == '\r') return;
        if (c == '\n') {
            StringBuffer line = lines.get(cursorLocation.getY());
            lines.add(cursorLocation.getY() + 1, new StringBuffer(line.substring(cursorLocation.getX())));
            line.delete(cursorLocation.getX(), line.length());
            setCursorLocation(new Location(0, cursorLocation.getY() + 1));
        } else {
            lines.get(cursorLocation.getY()).insert(cursorLocation.getX(), c);
            moveCursorRight(false);
        }
        notifyTextObservers();
    }
}
