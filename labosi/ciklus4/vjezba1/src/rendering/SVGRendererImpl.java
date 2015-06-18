package rendering;

import geometry.Point;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 6/18/15.
 */
public class SVGRendererImpl implements Renderer {

    private List<String> lines = new ArrayList<>();
    private String fileName;

    public SVGRendererImpl(String fileName) {
        // zapamti fileName; u lines dodaj zaglavlje SVG dokumenta:
        // <svg xmlns=... >
        // ...
        this.fileName = fileName;
        lines.add("<svg version=\"1.1\"\n" +
                "        baseprofile=\"full\"\n" +
                "        xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "        xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
                "        xmlns:ev=\"http://www.w3.org/2001/xml-events\">");

    }

    public void close() throws IOException {
        // u lines još dodaj završni tag SVG dokumenta: </svg>
        // sve retke u listi lines zapiši na disk u datoteku
        // ...
        lines.add("</svg>");
        Files.write(Paths.get(fileName), lines, Charset.defaultCharset());
    }

    @Override
    public void drawLine(Point s, Point e) {
        // Dodaj u lines redak koji definira linijski segment:
        // <line ... />
        lines.add(String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:rgb(0,0,255);stroke-width:2\" />",
                s.getX(),s.getY(),e.getX(),e.getY()));
    }

    @Override
    public void fillPolygon(Point[] points) {
        // Dodaj u lines redak koji definira popunjeni poligon:
        // <polygon points="..." style="stroke: ...; fill: ...;" />
        String pointsString = "";
        for (Point p:points)
            pointsString+=p.getX()+","+p.getY()+" ";
        lines.add(String.format("<polygon points=\"%s\" style=\"fill:blue;stroke:red;stroke-width:2\" />",pointsString));
    }

}