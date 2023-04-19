/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rv02
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] x;
    private static final double CONFIDENCE_95 = 1.96;
    
    public PercolationStats(int n, int trials) {
        if (trials <= 0 || n <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        this.trials = trials;
        this.x = new double[trials];
        for (int i = 0; i < trials; i++) {
            this.x[i] = (double) experiment(n) / (n * n);
        }
    }
    
    private int experiment(int n) {
        Percolation percolation = new Percolation(n);
        int[] arr = new int[n*n];
        int i, row, col;
        for (i = 0; i < n*n; i++) {
            arr[i] = i;
        }
        StdRandom.shuffle(arr);
        i = 0;
        while (!percolation.percolates()) {
            row = arr[i] / n + 1;
            col = arr[i] % n + 1;
            percolation.open(row, col);
            i++;
        }
        return percolation.numberOfOpenSites();
    }
    
    public double mean() {
        return StdStats.mean(this.x);
    }
    
    public double stddev() {
        return StdStats.stddev(this.x);
    }
    
    public double confidenceLo() {
        return this.mean() - CONFIDENCE_95 * stddev() / Math.sqrt(this.trials);
    }
    
    public double confidenceHi() {
        return this.mean() + CONFIDENCE_95 * stddev() / Math.sqrt(this.trials);
    }
    
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(String.format("%-23s %2s", "mean", "= ") + test.mean());
        System.out.println(String.format("%-23s %2s", "stddev", "= ") + test.stddev());
        System.out.print("95% confidence interval = [" + test.confidenceLo());
        System.out.println(", " + test.confidenceHi() + "]");
        }
}
