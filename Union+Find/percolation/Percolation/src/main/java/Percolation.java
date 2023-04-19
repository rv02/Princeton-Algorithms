import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF grid1;
    private boolean[] openStatus;
    private int openSitesCount;
    private final int sz;
    
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n <= 0 not allowed");
        this.grid = new WeightedQuickUnionUF(n * n + 2);
        this.grid1 = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < n; i++) {
            grid.union(i, n * n);
            grid1.union(i, n * n);
            grid.union(n * (n - 1) + i, n * n + 1);
        }
        this.openStatus = new boolean[n*n];
        for (int i = 0; i < n*n; i++) {
            this.openStatus[i] = false;
        }
        this.sz = n;
        this.openSitesCount = 0;
    }
    
    private void check(int row, int col) {
        if (row < 1  || row > this.sz || col < 1 || col > this.sz)
          throw new IllegalArgumentException("OutOfBound");
    }

    private int index(int row, int col) {
    	return (row - 1) * this.sz + col - 1;
    }
    
    public void open(int row, int col) {
        this.check(row, col);
        int currIndex = index(row, col);
        if (this.openStatus[currIndex]) {
            return;
        }
        this.openStatus[currIndex] = true;
        this.openSitesCount++;
        if (row != 1 && this.isOpen(row - 1, col)) {
            grid.union(currIndex, index(row - 1, col));
            grid1.union(currIndex, index(row - 1, col));
        }
        
        if (col != 1 && this.isOpen(row, col - 1)) {
            grid.union(currIndex, index(row, col - 1));
            grid1.union(currIndex, index(row, col - 1));
        }
        
        if (row != this.sz && this.isOpen(row + 1, col)) {
            grid.union(currIndex, index(row + 1, col));
            grid1.union(currIndex, index(row + 1, col));
        }
        
        if (col != this.sz && this.isOpen(row, col + 1)) {
            grid.union(currIndex, index(row, col + 1));
            grid1.union(currIndex, index(row, col + 1));
        }
    }
    
    public boolean isOpen(int row, int col) {
        this.check(row, col);
        return this.openStatus[index(row, col)];
    }
    
    public boolean isFull(int row, int col) {
        this.check(row, col);
        if (this.isOpen(row, col)) {
        	return grid1.find(index(row, col)) == grid1.find(sz*sz);
    	} else {
    		return false;
    	}
    }
    
    public int numberOfOpenSites() {
        return this.openSitesCount;
    }
    
    public boolean percolates() {
        if (this.sz == 1) {
            return isOpen(1, 1);
        }
        return grid.find(sz*sz) == grid.find(sz*sz+1);
    }
    
}