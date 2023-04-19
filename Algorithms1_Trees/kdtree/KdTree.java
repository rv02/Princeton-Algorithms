/*
 *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 ************************************************************************
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private int size;
    private Node root;

    private static class Node {
        private Point2D p;      // the point
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }
    }

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, true);
    }

    private Node insert(Node node, Point2D p, boolean alignment) {
        if (node == null) {
            size++;
            return new Node(p);
        }
        if (p.equals(node.p)) return node;
        if (alignment) {
            if (p.x() < node.p.x()) node.lb = insert(node.lb, p, false);
            else node.rt = insert(node.rt, p, false);
        } else {
            if (p.y() < node.p.y()) node.lb = insert(node.lb, p, true);
            else node.rt = insert(node.rt, p, true);
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(p) != null;
    }

    private Point2D get(Point2D p) { return get(root, p, true); }

    private Point2D get(Node node, Point2D p, boolean alignment) {
        if (node == null) return null;
        if (p.equals(node.p)) return p;
        if (alignment) {
            if (p.x() < node.p.x()) return get(node.lb, p, false);
            else return get(node.rt, p, false);
        } else {
            if (p.y() < node.p.y()) return get(node.lb, p, true);
            else return get(node.rt, p, true);
        }
    }

    public void draw() {
         draw(root, true, 0.0, 0.0, 1.0, 1.0);
    }

    private void draw(Node node, boolean alignment, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        StdDraw.setPenRadius();
        if (alignment) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), ymin, node.p.x(), ymax);
            draw(node.lb, false, xmin, ymin, node.p.x(), ymax);
            draw(node.rt, false, node.p.x(), ymin, xmax, ymax);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, node.p.y(), xmax, node.p.y());
            draw(node.lb, true, xmin, ymin, xmax, node.p.y());
            draw(node.rt, true, xmin, node.p.y(), xmax, ymax);
        }
    }

    private void getRange(Node node, SET<Point2D> pointsInRange, RectHV rect, boolean alignment) {
        if (node == null) return;
        Point2D p = node.p;
        if (rect.contains(p)) pointsInRange.add(p);
        boolean checkBoth = false;
        if (alignment) {
            if (p.x() <= rect.xmax() && p.x() >= rect.xmin()) checkBoth = true;
            if (checkBoth || rect.xmax() <= p.x()) getRange(node.lb, pointsInRange, rect, false);
            if (checkBoth || rect.xmin() >= p.x()) getRange(node.rt, pointsInRange, rect, false);
        } else {
            if (p.y() <= rect.ymax() && p.y() >= rect.ymin()) checkBoth = true;
            if (checkBoth || rect.ymax() <= p.y()) getRange(node.lb, pointsInRange, rect, true);
            if (checkBoth || rect.ymin() >= p.y()) getRange(node.rt, pointsInRange, rect, true);

        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> pointsInRange = new SET<>();
        getRange(root, pointsInRange, rect, true);
        return pointsInRange;
    }

    private Point2D nearest(Point2D p, RectHV rect, Point2D nearestPoint, Node node, boolean alignment) {
        if (node == null) return nearestPoint;
        double nearest = nearestPoint.distanceSquaredTo(p);
        if (!rect.contains(p) && Double.compare(nearest, rect.distanceSquaredTo(p)) < 0) return nearestPoint;
        Point2D curr = node.p;
        double currDist = curr.distanceSquaredTo(p);
        if (Double.compare(currDist, nearest) < 0) {
            nearestPoint = curr;
        }
        boolean checkLBFirst = false;
        if (alignment) {
            RectHV rectLB = new RectHV(rect.xmin(), rect.ymin(), curr.x(), rect.ymax());
            RectHV rectRT = new RectHV(curr.x(), rect.ymin(), rect.xmax(), rect.ymax());
            double distLB = rectLB.distanceSquaredTo(p);
            double distRT = rectRT.distanceSquaredTo(p);
            if (Double.compare(distLB, distRT) < 0) checkLBFirst = true;
            if (checkLBFirst) {
                nearestPoint = nearest(p, rectLB, nearestPoint, node.lb, false);
                nearestPoint = nearest(p, rectRT, nearestPoint, node.rt, false);
            } else {
                nearestPoint = nearest(p, rectRT, nearestPoint, node.rt, false);
                nearestPoint = nearest(p, rectLB, nearestPoint, node.lb, false);
            }
        } else {
            RectHV rectLB = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), curr.y());
            RectHV rectRT = new RectHV(rect.xmin(), curr.y(), rect.xmax(), rect.ymax());
            double distLB = rectLB.distanceSquaredTo(p);
            double distRT = rectRT.distanceSquaredTo(p);
            if (Double.compare(distLB, distRT) < 0) checkLBFirst = true;
            if (checkLBFirst) {
                nearestPoint = nearest(p, rectLB, nearestPoint, node.lb, true);
                nearestPoint = nearest(p, rectRT, nearestPoint, node.rt, true);
            } else {
                nearestPoint = nearest(p, rectRT, nearestPoint, node.rt, true);
                nearestPoint = nearest(p, rectLB, nearestPoint, node.lb, true);
            }
        }
        return nearestPoint;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        RectHV initialRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        Point2D nearestPoint = root.p;
        return nearest(p, initialRect, nearestPoint, root, true);
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
    /*
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));

        System.out.println(tree.size());
        System.out.println(tree.contains(new Point2D(0.7, 0.2)));
    */
        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            tree.insert(p);
        }
        StdOut.println(tree.nearest(new Point2D(0.81, 0.30)));
        //
        // tree.draw();

    }
}
