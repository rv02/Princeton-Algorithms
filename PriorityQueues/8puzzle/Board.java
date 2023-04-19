//import java.util.Iterator;
//import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[] tiles;
    private final int n;

    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n * n];
        int k = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.tiles[k++] = tiles[i][j];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[index(i, j)]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < n * n; i++) {
            if (tiles[i] == 0) continue;
            if (tiles[i] != i + 1)
                count++;
        }
        return count;
    }

    private int index(int row, int col) {
        return row * n + col;
    }

    private int row(int var) {
        return var / n;
    }

    private int col(int var) {
        return var % n;
    }

    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n * n; i++) {
                if (tiles[i] == 0) continue;
                int elem = tiles[i] - 1;
                sum += Math.abs(row(i) - row(elem)) + Math.abs(col(i) - col(elem));
        }
        return sum;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board comparedBoard = (Board) y;
        if (this.n != comparedBoard.n) return false;
        for (int i = 0; i < n * n; i++)
                if (this.tiles[i] != comparedBoard.tiles[i]) return false;
        return true;
    }

    private void exchange(int i, int j) {
        int temp = tiles[i];
        tiles[i] = tiles[j];
        tiles[j] = temp;
    }

    private Board makeBoard(int i, int j) {
        exchange(i, j);
        int[][] board = new int[n][n];
        int k = 0;
        for (int p =0; p < n; p++)
            for (int q = 0; q < n; q++)
                board[p][q] = tiles[k++];
        exchange(i, j);
        return new Board(board);
    }

    private Stack<Board> getNeighbors() {
        Stack<Board> neighbors = new Stack<>();
        int i;
        for (i = 0; i < n * n; i++)
            if (tiles[i] == 0) break;
        if (i >= n) neighbors.push(makeBoard(i, i - n));
        if (i < n * (n - 1)) neighbors.push(makeBoard(i, i + n));
        if (i % n != 0) neighbors.push(makeBoard(i, i - 1));
        if (i % n != n - 1) neighbors.push(makeBoard(i, i + 1));
        return neighbors;
    }


    public Iterable<Board> neighbors() {
        return getNeighbors();
        /*return () -> new Iterator<>() {
            private final Stack<Board> neighbors = getNeighbors();

            public boolean hasNext() {
                return !neighbors.isEmpty();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Board next() {
                if (!hasNext()) throw new NoSuchElementException();
                return neighbors.pop();
            }
        };*/
    }

    public Board twin() {
        if (tiles[0] != 0 && tiles[1] != 0) return makeBoard(0, 1);
        else return makeBoard(n * n - 1, n * n - 2);
    }

    public static void main(String[] args) {
        int[][] tiles1 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        int[][] perfect = {{4, 1, 3}, {0, 2, 5}, {7, 8, 6}};
        Board board1 = new Board(perfect);
        Board board = new Board(tiles1);
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board1.manhattan());
        System.out.println(board.isGoal());
        System.out.println(board1.isGoal());
        System.out.println(board.equals(board1));
        for (Board b : board1.neighbors()) {
            System.out.println(b);
        }
        System.out.println(board.twin());
        System.out.println(board1.dimension());
    }

}
