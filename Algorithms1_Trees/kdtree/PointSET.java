/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> point2DSET;

    public PointSET() {
        point2DSET = new SET<>();
    }

    public boolean isEmpty() {
        return point2DSET.isEmpty();
    }

    public int size() {
        return point2DSET.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        point2DSET.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return point2DSET.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : point2DSET)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> pointsInRange = new SET<>();
        for (Point2D p : point2DSET) {
            if (rect.contains(p)) pointsInRange.add(p);
        }
        return pointsInRange;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (point2DSET.isEmpty()) return null;
        Point2D nearest = point2DSET.min();
        for (Point2D point2D : point2DSET) {
             if (p.distanceSquaredTo(point2D) < p.distanceSquaredTo(nearest))
                 nearest = point2D;
        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}
