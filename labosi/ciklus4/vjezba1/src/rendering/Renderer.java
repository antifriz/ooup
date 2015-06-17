package rendering;

import geometry.Point;

/**
 * Created by ivan on 6/16/15.
 */
public interface Renderer {
    void drawLine(Point s, Point e);
    void fillPolygon(Point[] points);
}