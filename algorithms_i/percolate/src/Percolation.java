import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    final private WeightedQuickUnionUF grid;
    final private WeightedQuickUnionUF grid_top_only;
    final private boolean[][] isOpenArray;
    private int openSiteCount = 0;
    final private int size;

    final private int top;
    final private int bottom;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n >= 0");
        }
        size = n;
        isOpenArray = new boolean[n + 1][n + 1];
        grid = new WeightedQuickUnionUF(n * n + 2);
        grid_top_only = new WeightedQuickUnionUF(n * n + 2);

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                isOpenArray[row][col] = false;
            }
        }
        top = n * n;
        bottom = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openSiteCount += 1;
        int index = rcToIndex(row, col);

        if (row == 1) {
            grid.union(top, index);
            grid_top_only.union(top, index);
        }
        if (row == size) {
            grid.union(bottom, index);
        }
        int[][] d = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < d.length; i++) {
            int dRow = row + d[i][0];
            int dCol = col + d[i][1];
            if (dRow < 1 || dRow > size || dCol < 1 || dCol > size) {
                continue;
            }
            if (isOpenArray[dRow][dCol]) {
                int pt = rcToIndex(dRow, dCol);
                grid.union(pt, index);
                grid_top_only.union(pt, index);
            }
        }

        isOpenArray[row][col] = true;

    }

    /*
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * n = 3 x 3
     * 1,1 = 0
     * index = row * size + col;
     * 1*3 +1 = 4
     * 1*3 + 1 - 3 -1 = 0
     * */

    //    private  void print(){
//        int n=size;
//        for (int row = 0; row < n; row ++){
//            StringBuffer rs = new StringBuffer();
//            for(int col = 0; col<n; col++){
//                String c = "X ";
//                if (is_open_array[row][col]){
//                    c = "_ ";
//                }
//                rs.append(c);
//            }
//            System.out.println(rs.toString());
//        }
//        System.out.println(percolates()+"\n\n");
//    }
    // is the site (row, col){
    // open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return isOpenArray[row][col];
    }


    private void validate(int row, int col) {
        if (row < 1 || row >= size + 1) {
            throw new IllegalArgumentException("indexR " + row + " is not between 1 and " + (size));
        }
        if (col < 1 || col >= size + 1) {
            throw new IllegalArgumentException("indexC " + col + " is not between 1 and " + (size));
        }
    }

    private int rcToIndex(int row, int col) {
        int index = row * size + col - size - 1;
//        int index = row * size + col;
        return index;
    }

    // is the site (row, col){
    // full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = rcToIndex(row, col);
        return isOpen(row, col) && grid.connected(top, index) && grid_top_only.connected(top, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(top, bottom);
    }

    // test client (optional){

    public static void main(String[] args) {
        int n = 3;
        Percolation uf = new Percolation(n);
        System.out.println("isOpen:" + uf.isOpen(1, 1));
        System.out.println("isFull:" + uf.isFull(1, 1));
        System.out.println("\n");
        uf.open(1, 1);
        System.out.println("isOpen:" + uf.isOpen(1, 1));
        System.out.println("isFull:" + uf.isFull(1, 1));
        uf.open(2, 1);
        uf.open(3, 1);
        boolean x = uf.percolates();
        System.out.println(x);
    }
}