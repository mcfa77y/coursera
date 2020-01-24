import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private double[] arr;
    final private int size;
    final private double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException("bad n or trials: "+n+"\t"+trials);
        }
        arr = new double[trials];
        size = n;
        // open sites
//        while (percolation_count < trials) {
        for (int percolation_count = 0; percolation_count < trials; percolation_count += 1) {
            Percolation p = new Percolation(n);
            int i = 0;
            while (!p.percolates()) {
                int r = StdRandom.uniform(n) + 1;
                int c = StdRandom.uniform(n) + 1;
                if (!p.isOpen(r, c)) {
                    p.open(r, c);
                    i += 1;
                }
            }
            arr[percolation_count] = (i + 0.0) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arr);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(size));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(size));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(2, 10000);
        System.out.println("mean: " + ps.mean());
        System.out.println("stddev: " + ps.stddev());
        System.out.println("confidenceLo(): " + ps.confidenceLo());
        System.out.println("confidenceHi(): " + ps.confidenceHi());

    }

}