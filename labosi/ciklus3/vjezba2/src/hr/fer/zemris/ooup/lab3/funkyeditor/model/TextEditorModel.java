package hr.fer.zemris.ooup.lab3.funkyeditor.model;

import hr.fer.zemris.ooup.lab3.funkyeditor.helpers.Location;
import hr.fer.zemris.ooup.lab3.funkyeditor.helpers.LocationRange;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.funkyeditor.model.undomanager.UndoManager;

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
    private UndoManager undoManager;


    public TextEditorModel() {
        this.lines = new ArrayList<StringBuffer>();
        this.lines.add(new StringBuffer(""));

        this.cursorObservers = new ArrayList<CursorObserver>();
        this.textObservers = new ArrayList<TextObserver>();
        this.clipboardStack = new ClipboardStack();
        this.undoManager = UndoManager.getInstance();

        this.cursorLocation = new Location(0, 0);
        this.selectionRange = null;
    }

    public void setLines(String lines) {
        this.lines = new ArrayList<StringBuffer>();

        for (String line : lines.split(System.lineSeparator()))
            this.lines.add(new StringBuffer(line));

        this.cursorLocation = new Location(0, 0);
        this.selectionRange = null;
        notifyCursorObservers();
        notifyTextObservers();
    }

    public int lineLength(int i) {
        assert i < lines.size();
        return lines.get(i).length();
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }


    public void attachCursorObserver(CursorObserver observer) {
        cursorObservers.add(observer);
    }

    public void detachCursorObserver(CursorObserver observer) {
        cursorObservers.remove(observer);
    }

    public void notifyCursorObservers() {
        for (CursorObserver observer : cursorObservers)
            observer.onUpdateCursorLocation(cursorLocation);
    }

    public void setCursorLocation(Location cursorLocation) {
        this.cursorLocation = cursorLocation;
        notifyCursorObservers();
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
        Location nextLocation = getLeftLocation(cursorLocation);
        if (nextLocation == null) return;
        cursorLocation = nextLocation;
        processCursorMove(selectMode, lastLocation);
    }

    public void moveCursorRight(boolean selectMode) {
        Location lastLocation = cursorLocation.copy();
        Location nextLocation = getRightLocation(cursorLocation);
        if (nextLocation == null) return;
        cursorLocation = nextLocation;
        processCursorMove(selectMode, lastLocation);
    }

    public Location getLeftLocation(Location location) {
        Location lastLocation = location.copy();

        if (lastLocation.getX() == 0) {
            if (lastLocation.getY() == 0)
                return null;
            lastLocation.decY();
            lastLocation.setX(lineLength(lastLocation.getY()));
        } else {
            lastLocation.decX();
        }

        return lastLocation;
    }

    public Location getRightLocation(Location location) {
        Location lastLocation = location.copy();

        if (lastLocation.getX() >= lineLength(lastLocation.getY())) {
            if (lastLocation.getY() + 1 >= lines.size())
                return null;
            lastLocation.incY();
            lastLocation.setX(0);
        }
        lastLocation.incX();

        return lastLocation;
    }


    public boolean isSelectedModeActive() {
        return selectionRange != null;
    }


    public LocationRange getSelectionRange() {
        return selectionRange.copy();
    }

    public void setSelectionRange(LocationRange r) {
        selectionRange = r;
        notifyTextObservers();
    }

    public void processCursorMove(boolean isSelectMode, Location last) {
        if (isSelectMode) appendToSelection(last);
        else clearSelection();

        notifyCursorObservers();
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

    public void attachTextObserver(TextObserver observer) {
        textObservers.add(observer);
    }

    public void detachTextObserver(TextObserver observer) {
        textObservers.remove(observer);
    }

    public void notifyTextObservers() {
        for (TextObserver observer : textObservers)
            observer.onUpdateTextObserver();
    }

    public Character deleteBefore(Location location) {
        Location locationBefore = getLeftLocation(location);
        if (locationBefore == null)
            return null;
        return deleteAfter(locationBefore);
    }

    public Character deleteAfter(Location location) {
        final char deleted;

        if (location.getX() == lineLength(location.getY())) {
            if (location.getY() == lines.size() - 1)
                return null;
            lines.get(location.getY()).append(lines.get(location.getY() + 1));
            deleted = '\n';
            lines.remove(location.getY() + 1);
        } else {
            deleted = lines.get(location.getY()).charAt(location.getX());
            lines.get(location.getY()).deleteCharAt(location.getX());
        }
        return deleted;
    }

    public void deleteBefore() {
        if (getLeftLocation(cursorLocation) != null) {
            EditAction ea = new EditAction() {
                Location beforeLocation = cursorLocation.copy();
                Location afterLocation;
                Character c;

                @Override
                public void executeDo() {
                    afterLocation = TextEditorModel.this.getLeftLocation(beforeLocation);
                    c = deleteBefore(beforeLocation);
                    TextEditorModel.this.notifyTextObservers();
                    TextEditorModel.this.setCursorLocation(afterLocation);
                }

                @Override
                public void executeUndo() {
                    TextEditorModel.this.insert(c, afterLocation);
                    TextEditorModel.this.notifyTextObservers();
                    TextEditorModel.this.setCursorLocation(beforeLocation);
                }
            };
            ea.executeDo();
            undoManager.push(ea);
        }
    }

    public void deleteAfter() {
        if (getRightLocation(cursorLocation) != null) {
            EditAction ea = new EditAction() {
                Location location = cursorLocation.copy();
                Character c;

                @Override
                public void executeDo() {
                    c = deleteAfter(location);
                    TextEditorModel.this.notifyTextObservers();
                    TextEditorModel.this.setCursorLocation(location);
                }

                @Override
                public void executeUndo() {
                    TextEditorModel.this.insert(c, location);
                    TextEditorModel.this.notifyTextObservers();
                    TextEditorModel.this.setCursorLocation(location);
                }
            };
            ea.executeDo();
            undoManager.push(ea);
        }
    }


    public String deleteRange(LocationRange r) {

        final String deleted = getSelectionString();
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

        return deleted;
    }

    public void deleteSelection() {
        if (isSelectedModeActive()) {
            EditAction ea = new EditAction() {
                LocationRange beforeSelectionRange = TextEditorModel.this.selectionRange.copy();
                String deletedString;

                @Override
                public void executeDo() {
                    deletedString = deleteRange(selectionRange);
                    TextEditorModel.this.selectionRange = null;
                    TextEditorModel.this.notifyTextObservers();
                    TextEditorModel.this.setCursorLocation(beforeSelectionRange.getLower().copy());
                }

                @Override
                public void executeUndo() {
                    insert(deletedString, beforeSelectionRange.getLower());
                    TextEditorModel.this.selectionRange = beforeSelectionRange;
                    TextEditorModel.this.notifyTextObservers();
                    TextEditorModel.this.setCursorLocation(beforeSelectionRange.getTo().copy());
                }
            };
            ea.executeDo();
            undoManager.push(ea);
        }
    }

    public void clearSelection() {
        if (selectionRange == null)
            return;
        selectionRange = null;
        notifyTextObservers();
    }


    public Location insert(String str, Location location) {
        Location tmpLocation = location.copy();
        for (char c : str.toCharArray())
            tmpLocation = insert(c, tmpLocation);
        return tmpLocation;
    }

    public void insert(String str) {
        assert str != null;
        if (isSelectedModeActive())
            deleteSelection();
        final String strr = str;
        EditAction ea = new EditAction() {
            Location beforeLocation = cursorLocation.copy();
            Location afterLocation;
            String strInserted = strr;

            @Override
            public void executeDo() {
                afterLocation = insert(strInserted, beforeLocation);
                TextEditorModel.this.notifyTextObservers();
                TextEditorModel.this.setCursorLocation(afterLocation);
            }

            @Override
            public void executeUndo() {
                TextEditorModel.this.selectionRange = null;
                for (int i = 0; i < strInserted.length(); i++)
                    deleteAfter(beforeLocation);
                TextEditorModel.this.notifyTextObservers();
                TextEditorModel.this.setCursorLocation(beforeLocation);
            }
        };
        ea.executeDo();
        undoManager.push(ea);
    }

    public Location insert(char c, Location location) {
        if (c == '\n') {
            StringBuffer line = lines.get(location.getY());
            lines.add(location.getY() + 1, new StringBuffer(line.substring(location.getX())));
            line.delete(location.getX(), line.length());
            return new Location(0, location.getY() + 1);
        } else {
            lines.get(location.getY()).insert(location.getX(), c);
            return getRightLocation(location);
        }
    }


    public void insert(char c) {
        if (isSelectedModeActive())
            deleteSelection();
        final char cc = c;
        EditAction ea = new EditAction() {
            Location beforeLocation = cursorLocation.copy();
            Location afterLocation;
            Character cInserted = cc;

            @Override
            public void executeDo() {
                afterLocation = TextEditorModel.this.insert(cInserted, beforeLocation);
                TextEditorModel.this.notifyTextObservers();
                TextEditorModel.this.setCursorLocation(afterLocation);
            }

            @Override
            public void executeUndo() {
                TextEditorModel.this.selectionRange = null;
                deleteBefore(afterLocation);
                TextEditorModel.this.notifyTextObservers();
                TextEditorModel.this.setCursorLocation(beforeLocation);
            }
        };
        ea.executeDo();
        undoManager.push(ea);
    }

    private void pushSelectionToClipboard() {
        if (!isSelectedModeActive()) return;

        String str = getSelectionString();
        getClipboardStack().push(str);
    }

    private String getSelectionString() {
        int ly = selectionRange.getLower().getY();
        int lx = selectionRange.getLower().getX();
        int hx = selectionRange.getHigher().getX();
        int hy = selectionRange.getHigher().getY();
        boolean oneLiner = ly == hy;

        StringBuilder sb = new StringBuilder();

        if (oneLiner) sb.append(lines.get(ly).substring(lx, hx));
        else {
            sb.append(lines.get(ly).substring(lx));
            sb.append(System.lineSeparator());

            Iterator<StringBuffer> it = linesRange(selectionRange.getLower().getY() + 1, selectionRange.getHigher().getY());
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(System.lineSeparator());
            }
            assert hy < lines.size();

            sb.append(lines.get(hy).substring(0, hx));
        }
        return sb.toString();
    }

    public void copySelection() {
        pushSelectionToClipboard();
    }

    public void cutSelection() {
        pushSelectionToClipboard();
        deleteSelection();
    }

    public void paste() {
        if (getClipboardStack().isEmpty()) return;
        insert(getClipboardStack().peek());
    }

    public Location getEOF() {
        return new Location(lineLength(lines.size() - 1), lines.size() - 1);
    }

    public void selectAll() {
        Location eof = getEOF();
        selectionRange = new LocationRange(new Location(0, 0), eof);
        notifyTextObservers();
        setCursorLocation(eof);
    }

    public void pasteSpecial() {
        if (getClipboardStack().isEmpty()) return;
        insert(getClipboardStack().pop());
    }

    public ClipboardStack getClipboardStack() {
        return clipboardStack;
    }

    public String getLinesString() {
        StringBuilder sb = new StringBuilder();
        Iterator<StringBuffer> it = allLines();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void moveCursorStart() {
        setCursorLocation(new Location(0, 0));
    }

    public void moveCursorEnd() {
        setCursorLocation(getEOF());
    }
}
