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
    private ClipboardStack clipboardStack;


    public TextEditorModel(String lines) {
        this.lines = new ArrayList<StringBuffer>();

        for (String line : lines.split(System.lineSeparator()))
            this.lines.add(new StringBuffer(line));

        this.cursorLocation = new Location(0, 0);
        this.selectionRange = null;
        this.cursorObservers = new ArrayList<CursorObserver>();
        this.textObservers = new ArrayList<TextObserver>();
        this.clipboardStack = new ClipboardStack();
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

    public void attach(CursorObserver observer) {
        cursorObservers.add(observer);
    }

    public void detach(CursorObserver observer) {
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
            int i1 = index1;
            int i2 = index2;

            @Override
            public boolean hasNext() {
                return i1 < i2 && i1 < lines.size();
            }

            @Override
            public StringBuffer next() {
                assert hasNext();
                return lines.get(i1++);
            }

            @Override
            public void remove() {
                assert hasNext();
                lines.remove(i1);
                i2--;
            }
        };
    }

    public void attach(TextObserver observer) {
        textObservers.add(observer);
    }

    public void detach(TextObserver observer) {
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


        setCursorLocation(r.getLower().copy());

        int ly = r.getLower().getY();
        int lx = r.getLower().getX();
        int hx = r.getHigher().getX();
        int hy = r.getHigher().getY();
        boolean oneLiner = ly == hy;

        if (oneLiner) lines.get(ly).delete(lx, hx);
        else {
            lines.get(ly).delete(lx, lines.get(ly).length());

            Iterator<StringBuffer> it = linesRange(ly + 1, hy);
            while (it.hasNext()) it.remove();

            StringBuffer nextLine = lines.get(ly + 1);
            nextLine.delete(0, hx);
            lines.get(ly).append(nextLine);
            lines.remove(ly + 1);
        }
        clearSelection();

        notifyTextObservers();
    }

    public void deleteSelection() {
        if (isSelectedModeActive())
            deleteRange(selectionRange);
    }

    public void clearSelection() {
        if (selectionRange == null)
            return;
        selectionRange = null;
        notifyTextObservers();
    }

    public void insert(String str) {
        for (char c : str.toCharArray())
            insert(c);
    }

    public void insert(char c) {
        deleteSelection();

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

    private void pushSelectionToClipboard() {
        if (!isSelectedModeActive()) return;

        int ly = selectionRange.getLower().getY();
        int lx = selectionRange.getLower().getX();
        int hx = selectionRange.getHigher().getX();
        int hy = selectionRange.getHigher().getY();
        boolean oneLiner = ly == hy;

        StringBuilder sb = new StringBuilder();

        if (oneLiner) sb.append(lines.get(ly).substring(lx, hx));
        else {
            System.out.println(""+selectionRange.getLower()+" " + selectionRange.getHigher());

            sb.append(lines.get(ly).substring(lx));
            sb.append(System.lineSeparator());

            Iterator<StringBuffer> it = linesRange(selectionRange.getLower().getY() + 1, selectionRange.getHigher().getY());
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(System.lineSeparator());
            }
            assert hy<lines.size();

            sb.append(lines.get(hy).substring(0, hx));
        }
        getClipboardStack().push(sb.toString());
    }

    public void copySelection() {
        pushSelectionToClipboard();
    }

    public void cutSelection() {
        pushSelectionToClipboard();
        deleteSelection();
    }

    public void paste() {
        insert(getClipboardStack().peek());
    }

    public void pasteSpecial() {
        insert(getClipboardStack().pop());
    }

    public ClipboardStack getClipboardStack() {
        return clipboardStack;
    }
}
