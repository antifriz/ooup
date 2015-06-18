package rendering;

import geometry.Point;

import java.awt.*;

/**
 * Created by ivan on 6/16/15.
 */
public class G2DRendererImpl implements Renderer {

    private Graphics2D g2d;

    public G2DRendererImpl(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void drawLine(Point s, Point e) {
        g2d.setColor(Color.blue);
        Stroke tmp =g2d.getStroke();
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(s.getX(),s.getY(),e.getX(),e.getY());
        g2d.setStroke(tmp);
    }

    @Override
    public void fillPolygon(Point[] points) {
        int[] xs =new int[points.length];
        int[] ys =new int[points.length];
        int i =0;
        for (Point p : points)
        {
            xs[i]=p.getX();
            ys[i]=p.getY();
            i++;
        }

        g2d.setColor(Color.blue);
        g2d.fillPolygon(xs, ys, points.length);
        g2d.setColor(Color.red);
        Stroke tmp =g2d.getStroke();
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(xs,ys,points.length);
        g2d.setStroke(tmp);

    }

}
