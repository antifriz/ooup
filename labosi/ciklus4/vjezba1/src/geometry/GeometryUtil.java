package geometry;

/**
 * Created by ivan on 6/16/15.
 */
public class GeometryUtil {

    public static double distanceFromPoint(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.getX()-point2.getX(),2)+Math.pow(point1.getY()-point2.getY(),2));
    }


    public static double distanceFromLineSegment(Point s, Point e, Point p) {

        double determinant = s.getX()*e.getY() - s.getY()*e.getX();

        double determinant_x = s.getY()-e.getY();
        double determinant_y = e.getX()-s.getX();

        double A = determinant_x/determinant;
        double B = determinant_y/determinant;

        double lineDistance = Math.abs(A*p.getX()+B*p.getY()+1)/Math.sqrt(A*A+B*B);

        double startDistance = distanceFromPoint(s,p);
        double endDistance = distanceFromPoint(e,p);

        double lineLength = distanceFromPoint(s,e);
        double startLineDistance = Math.sqrt(startDistance * startDistance - lineDistance * lineDistance);
        double endLineDistance = Math.sqrt(endDistance*endDistance - lineDistance*lineDistance);
        if(Math.abs(startDistance+endLineDistance-lineLength) <=Math.pow(10,-2))
            return lineDistance;

        return Math.min(startDistance,endDistance);
    }
}
