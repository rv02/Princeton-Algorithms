import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
//import java.util.Iterator;

public class Solver {
    private Node min;
    private final boolean solvable;

    private static class Node {
        private final Board board;
        private final short moves;
        private final Node prev;
        private final short manhattan;

        private Node(Board board, short moves, Node prev, short manhattan) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = manhattan;
        }

    }

    private static class PriorityByManhattan implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            int c1 = node1.manhattan + node1.moves;
            int c2 = node2.manhattan + node2.moves;
            return c1 - c2;
        }
    }


    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        Comparator<Node> comparator = new PriorityByManhattan()
                .thenComparingInt((Node n) -> n.manhattan);

        min = new Node(initial, (short) 0, null, (short) initial.manhattan());
        MinPQ<Node> minPQ = new MinPQ<>(comparator);
        minPQ.insert(min);

        Board twin = initial.twin();
        Node twinMin = new Node(twin, (short) 0, null, (short) twin.manhattan());
        MinPQ<Node> twinMinPQ = new MinPQ<>(comparator);
        twinMinPQ.insert(twinMin);

        while (true) {

            min = minPQ.delMin();
            if (min.manhattan == 0) break;
            for (Board neighbor : min.board.neighbors()) {
                short mh = (short) neighbor.manhattan();
                if (min.prev == null || !neighbor.equals(min.prev.board))
                    minPQ.insert(new Node(neighbor, (short) (min.moves + 1), min, mh));
            }

            twinMin = twinMinPQ.delMin();
            if (twinMin.manhattan == 0) break;
            for (Board neighbor : twinMin.board.neighbors()) {
                short mh = (short) neighbor.manhattan();
                if (twinMin.prev == null || !neighbor.equals(twinMin.prev.board))
                    twinMinPQ.insert(new Node(neighbor, (short) (twinMin.moves + 1), twinMin, mh));
            }

        }
            solvable = min.board.isGoal();
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!solvable) return -1;
        return min.moves;
    }

    public Iterable<Board> solution() {
        if (!solvable) return null;

        Stack<Board> stack = new Stack<>();
        Node node = min;
        while (node != null) {
            stack.push(node.board);
            node = node.prev;
        }
        return stack;


        /*return () -> new Iterator<>() {
            private final Stack<Board> stack = getStack();

            private Stack<Board> getStack() {
                Stack<Board> stack = new Stack<>();
                Node node = min;
                while (node != null) {
                    stack.push(node.board);
                    node = node.prev;
                }
                return stack;
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Board next() {
                return stack.pop();
            }
        };*/
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
