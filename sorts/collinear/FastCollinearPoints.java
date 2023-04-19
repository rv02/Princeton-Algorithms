/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;
    private int size;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        if (Arrays.asList(points).contains(null)) throw new IllegalArgumentException();
        int n = points.length;
        Point[] points1 = Arrays.copyOf(points, n);
        if (checkDuplicates(points1)) throw new IllegalArgumentException();
        size = 0;
        lineSegments = new LineSegment[n * n];
        for (int i = 0; i < n - 3; i++) {
            Arrays.sort(points1);
            Comparator<Point> comparator = points1[i].slopeOrder();
            Arrays.sort(points1, i + 1, n, comparator);
            Point[] temp = new Point[n];
            int tempCount = 0;
            for (int j = i + 2; j < n; j++) {
                if (j == n - 1 && comparator.compare(points1[j], points1[j - 1]) == 0) temp[tempCount++] = points1[j];
                if (j != n - 1 && comparator.compare(points1[j], points1[j - 1]) == 0) {
                    temp[tempCount++] = points1[j - 1];
                    continue;
                }
                if (comparator.compare(points1[j - 1], points1[j - 2]) == 0) temp[tempCount++] = points1[j - 1];
                if (tempCount >= 3) {
                    Arrays.sort(temp, 0, tempCount);
                    if (checkSlopes(points1, i, temp[tempCount - 1])) {
                        lineSegments[size++] = new LineSegment(points1[i], temp[tempCount - 1]);
                    }
                }
                temp = new Point[n];
                tempCount = 0;
            }

        }
    }

    private static boolean checkSlopes(Point[] points, int i, Point a) {
        double slope1 = a.slopeTo(points[i]);
        for (int j = 0; j < i; j++) {
            if (slope1 == a.slopeTo(points[j])) return false;
        }
        return true;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
