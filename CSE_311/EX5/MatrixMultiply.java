import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiply {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.multiply.
    // Ignoring the initialization of arrays, your implementation should have n^3 work and log(n) span
    public static int[][] multiply(int[][] a, int[][] b, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        int[][] product = new int[a.length][b[0].length];
        POOL.invoke(new MatrixMultiplyAction(a, b, product, 0, a.length, 0, b[0].length)); // TODO: add parameters to match your constructor
        return product;
    }

    // Behavior should match the 2d version of Sequential.dotProduct.
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[][] a, int[][] b, int row, int col, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(a[row], getColumn(b, col), 0, a[0].length)); 
    }   

    private static int[] getColumn(int[][] b, int col){
        int[] column = new int[b.length];
        for (int i = 0; i < b.length; i++){
            column[i] = b[i][col];
        }
        return column;
    }


    private static class MatrixMultiplyAction extends RecursiveAction{
        int[][] a, b, product;
        int rowStart, rowEnd, colStart, colEnd;

        public MatrixMultiplyAction(int[][] a, int[][] b, int[][] product, int rowStart, int rowEnd, int colStart, int colEnd){
            this.a = a;
            this.b = b;
            this.product = product;
            this.rowStart = rowStart;
            this.rowEnd = rowEnd;
            this.colStart = colStart;
            this.colEnd = colEnd;
        }

        public void compute(){
            int rows = rowEnd - rowStart;
            int cols = colEnd - colStart;
            if (rows * cols <= CUTOFF) {
                for (int i = rowStart; i < rowEnd; i++) {
                    for (int j = colStart; j < colEnd; j++) {
                        product[i][j] = dotProduct(a, b, i, j, CUTOFF);
                    }
                }
            } else if (rows >= cols) {
                int mid = (rowStart + rowEnd) / 2;
                MatrixMultiplyAction top = new MatrixMultiplyAction(a, b, product, rowStart, mid, colStart, colEnd);
                MatrixMultiplyAction bottom = new MatrixMultiplyAction(a, b, product, mid, rowEnd, colStart, colEnd);
                top.fork();
                bottom.compute();
                top.join();
            } else {
                int mid = (colStart + colEnd) / 2;
                MatrixMultiplyAction left = new MatrixMultiplyAction(a, b, product, rowStart, rowEnd, colStart, mid);
                MatrixMultiplyAction right = new MatrixMultiplyAction(a, b, product, rowStart, rowEnd, mid, colEnd);
                left.fork();
                right.compute();
                left.join();
            }
        }

    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        int[] a, b;
        int low, high;

        public DotProductTask(int[] a, int[] b, int low, int high){
            this.a = a;
            this.b = b;
            this.low = low;
            this.high = high;
        }

        public Integer compute(){
            if (high - low <= CUTOFF) {
                int sum = 0;
                for (int i = low; i < high; i++) {
                    sum += a[i] * b[i];
                }
                return (sum);
            }
            int mid = (low + high) / 2;
            DotProductTask left = new DotProductTask(a, b, low, mid);
            DotProductTask right = new DotProductTask(a, b, mid, high);
            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();
            return (leftResult + rightResult);
            
        }
    }
    
}
