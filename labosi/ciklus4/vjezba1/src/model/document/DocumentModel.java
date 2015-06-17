package model.document;

import geometry.GeometryUtil;
import geometry.Point;
import graphicalobjects.GraphicalObject;
import graphicalobjects.GraphicalObjectListener;

import javax.print.Doc;
import java.util.*;


/**
 * Created by ivan on 6/16/15.
 */
public class DocumentModel {

    public final static double SELECTION_PROXIMITY = 20;

    // Kolekcija svih grafičkih objekata:
    private List<GraphicalObject> objects = new ArrayList<>();
    // Read-Only proxy oko kolekcije grafičkih objekata:
    private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
    // Kolekcija prijavljenih promatrača:
    private List<DocumentModelListener> listeners = new ArrayList<>();
    // Kolekcija selektiranih objekata:
    private List<GraphicalObject> selectedObjects = new ArrayList<>();
    // Read-Only proxy oko kolekcije selektiranih objekata:
    private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

    // Promatrač koji će biti registriran nad svim objektima crteža...
    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {

        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            DocumentModel.this.notifyListeners();
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            DocumentModel.this.notifyListeners();
        }
    };

    // Konstruktor...
    public DocumentModel() {

    }

    // Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
    // i potom obavijeste svi promatrači modela
    public void clear() {
    }

    // Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte model kao promatrača)
    public void addGraphicalObject(GraphicalObject obj) {
        objects.add(obj);
        if (obj.isSelected())
            selectedObjects.add(obj);
        obj.addGraphicalObjectListener(goListener);
        System.out.println("added, now:"+objects.size());
        notifyListeners();
    }

    // Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte model kao promatrača)
    public void removeGraphicalObject(GraphicalObject obj) {
        obj.removeGraphicalObjectListener(goListener);
        if (obj.isSelected())
            selectedObjects.remove(obj);
        objects.remove(obj);
        notifyListeners();
    }

    public void selectObject(GraphicalObject obj, boolean deselectOthers) {
        if (deselectOthers) {
            deselectAll();
        }
        selectedObjects.add(obj);
        obj.setSelected(true);
        notifyListeners();
    }

    public void deselectAll() {
        for (GraphicalObject o : selectedObjects)
            o.setSelected(false);
        selectedObjects.clear();
    }

    public void selectObject(Point mousePoint, boolean deselectOthers){
        GraphicalObject go =findSelectedGraphicalObject(mousePoint);
        if(go!=null)
            selectObject(go,deselectOthers);
    }

    // Vrati nepromjenjivu listu postojećih objekata (izmjene smiju ići samo kroz metode modela)
    public List list() {
        return roObjects;
    }

    // Prijava...
    public void addDocumentModelListener(DocumentModelListener l) {
        listeners.add(l);
    }

    // Odjava...
    public void removeDocumentModelListener(DocumentModelListener l) {
        listeners.remove(l);
    }

    // Obavještavanje...
    public void notifyListeners() {
        for (DocumentModelListener l : listeners)
            l.documentChange();
    }

    // Vrati nepromjenjivu listu selektiranih objekata
    public List<GraphicalObject> getSelectedObjects() {
        return roSelectedObjects;
    }


    // Vrati nepromjenjivu listu objekata
    public List<GraphicalObject> getAllObjects() {
        return roObjects;
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
    // Time će se on iscrtati kasnije (pa će time možda veći dio biti vidljiv)
    public void increaseZ(GraphicalObject go) {
        int idx = objects.indexOf(go);
        if(idx+1<objects.size())
        {
            System.out.println("incre");
            for(GraphicalObject o : objects)
                System.out.println(o.getShapeName());
            System.out.println(idx+" "+(idx+1));

            Collections.swap(objects, idx, idx + 1);
            for(GraphicalObject o : objects)
                System.out.println(o.getShapeName());
        }
        notifyListeners();
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto ranije...
    public void decreaseZ(GraphicalObject go) {
        int idx = objects.indexOf(go);
        if(0<=idx-1){
            System.out.println("decre");
            for(GraphicalObject o : objects)
                System.out.println(o.getShapeName());
            System.out.println(idx+" "+(idx-1));
            Collections.swap(objects,idx-1,idx);
            for(GraphicalObject o : objects)
                System.out.println(o.getShapeName());
        }
        notifyListeners();
    }

    // Pronađi postoji li u modelu neki objekt koji klik na točku koja je
    // predana kao argument selektira i vrati ga ili vrati null. Točka selektira
    // objekt kojemu je najbliža uz uvjet da ta udaljenost nije veća od
    // SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        GraphicalObject go = null;
        double proximity = Double.MAX_VALUE;

        for (GraphicalObject o : objects) {
            double tmpProximity = Double.MAX_VALUE;
            if(o.getBoundingBox().isPointInside(mousePoint))
                return o;
            Iterator<Point> it = o.getHotPointsIterator();
            while (it.hasNext())
            {
                double dist =GeometryUtil.distanceFromPoint(it.next(),mousePoint);
                if(dist<SELECTION_PROXIMITY && dist<proximity) {
                    proximity = tmpProximity;
                    go = o;
                }
            }
        }
        return go;
    }

    public GraphicalObject isSingleSelection(){
        if(selectedObjects.size() == 1)
            return selectedObjects.get(0);
        return null;
    }

    // Pronađi da li u predanom objektu predana točka miša selektira neki hot-point.
    // Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
    // udaljenost nije veća od SELECTION_PROXIMITY. Vraća se indeks hot-pointa
    // kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije
    // se pri tome NE dira.
    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        int idx = -1;
        double proximity = Double.MAX_VALUE;
        for (int i = 0; i < object.getNumberOfHotPoints(); i++) {

            //if (object.isHotPointSelected(i)) {
                System.out.println("From: "+ i+" to: "+mousePoint);
                double dist = object.getHotPointDistance(i, mousePoint);
                if (dist <= SELECTION_PROXIMITY && dist < proximity) {
                    idx = i;
                    proximity = dist;
                }
            //}
        }
        return idx;
    }

}
