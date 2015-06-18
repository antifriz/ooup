package geometry;

/**
 * Created by ivan on 6/16/15.
 */
public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(Point p1, Point p2) {
        this.x = Math.min(p1.getX(), p2.getX());
        this.y = Math.min(p1.getY(), p2.getY());
        this.width = Math.abs(p1.getX() - p2.getX());
        this.height = Math.abs(p1.getY() - p2.getY());
    }


    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public boolean isPointInside(Point p) {
        return x <= p.getX() && p.getX() < x + width && y <= p.getY() && p.getY() < y + height;
    }

    public double pointDistance(Point p) {
        if (isPointInside(p))
            return 0;
        Point topLeft = new Point(getX(), getY());
        Point topRight = new Point(getX() + getWidth(), getY());
        Point bottomLeft = new Point(getX(), getY() + getHeight());
        Point bottomRight = new Point(getX() + getWidth(), getY() + getHeight());
        return Math.min(
                Math.min(
                        GeometryUtil.distanceFromLineSegment(topLeft, topRight, p),
                        GeometryUtil.distanceFromLineSegment(topRight, bottomRight, p)),
                Math.min(
                        GeometryUtil.distanceFromLineSegment(bottomRight, bottomLeft, p),
                        GeometryUtil.distanceFromLineSegment(bottomLeft, topLeft, p))
        );

    }

    public Rectangle union(Rectangle other) {
        int firstPointX = Math.min(this.getX(), other.getX());
        int firstPointY = Math.min(this.getY(), other.getY());
        int secondPointX = Math.max(this.getX() + this.getWidth(), other.getX() + other.getWidth());
        int secondPointY = Math.max(this.getY() + this.getHeight(), other.getY() + other.getHeight());

        return new Rectangle(firstPointX, firstPointY, secondPointX - firstPointX, secondPointY - firstPointY);
    }

    public Rectangle scale(double factor) {
        width *= factor;
        height *= factor;
        return this;
    }
}