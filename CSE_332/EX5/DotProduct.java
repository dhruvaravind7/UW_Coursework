import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DotProduct {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.dotProduct
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[] a, int[]b, int cutoff){
        DotProduct.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(a, b, 0, a.length)); // TODO: add parameters to match your constructor
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
