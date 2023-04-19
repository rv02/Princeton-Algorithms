/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int size;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        if (Arrays.asList(points).contains(null)) throw new IllegalArgumentException();
        if (checkDuplicates(points)) throw new IllegalArgumentException();
        size = 0;
        lineSegments = new LineSegment[points.length];
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double slope1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < n; k++) {
                    double slope2 = points[i].slopeTo(points[k]);
                    if (slope1 != slope2) continue;
                    for (int l = k + 1; l < n; l++) {
                        double slope3 = points[i].slopeTo(points[l]);
                        if (slope2 == slope3) {
                            Point[] p = { points[i], points[j], points[k], points[l] };
                            Arrays.sort(p);
                            lineSegments[size++] = new LineSegment(p[0], p[3]);
                            if (size == lineSegments.length) {
                                lineSegments = Arrays.copyOf(lineSegments, n + size);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean checkDuplicates(Point[] points) {
        for (int j = 0; j < points.length; j++)
            for (int k = j + 1; k < points.length; k++)
                if (k != j && points[k].compareTo(points[j]) == 0) return true;
        return false;
    }

    public int numberOfSegments() {
        return size;
    }

    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[size];
        for (int i = 0; i < size; i++)
            ls[i] = lineSegments[i];
        return ls;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
