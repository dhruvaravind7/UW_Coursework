import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FilterEmpty {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    public static String[] filterEmpty(String[] arr, int cutoff) {
        FilterEmpty.CUTOFF = cutoff;

        int[] mapped = new int[arr.length];
        POOL.invoke(new MapEmptyTask(arr, mapped, 0, arr.length));

        int[] suffixSum = ParallelSuffix.suffixSum(mapped, cutoff);
        String[] filtered = new String[suffixSum[0]];

        POOL.invoke(new CopyFilteredTask(arr, suffixSum, mapped, filtered, 0, arr.length));
        return filtered;
    }

    private static class MapEmptyTask extends RecursiveAction {
        private String[] input;
        private int[] output;
        private int lo;
        private int hi;

        public MapEmptyTask(String[] input, int[] output, int lo, int hi) {
            this.input = input;
            this.output = output;
            this.lo = lo;
            this.hi = hi;
        }

        public void compute() {
            if (hi - lo <= CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    output[i] = input[i].isEmpty() ? 0 : 1;
                }
            } else {
                int mid = (lo + hi) / 2;
                MapEmptyTask left = new MapEmptyTask(input, output, lo, mid);
                MapEmptyTask right = new MapEmptyTask(input, output, mid, hi);
                left.fork();
                right.compute();
                left.join();
            }
        }
    }

    private static class CopyFilteredTask extends RecursiveAction {
        String[] input;
        int[] suffixSum;
        int[] mapped;
        String[] output;
        int lo;
        int hi;

        public CopyFilteredTask(String[] input, int[] suffixSum, int[] mapped, String[] output, int lo, int hi) {
            this.input = input;
            this.suffixSum = suffixSum;
            this.mapped = mapped;
            this.output = output;
            this.lo = lo;
            this.hi = hi;
        }

        public void compute() {
            if (hi - lo <= CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    if (mapped[i] == 1) {
                        int index = suffixSum[i] - 1;
                        output[output.length - 1 - index] = input[i];
                    }
                }
            } else {
                int mid = (lo + hi) / 2;
                CopyFilteredTask left = new CopyFilteredTask(input, suffixSum, mapped, output, lo, mid);
                CopyFilteredTask right = new CopyFilteredTask(input, suffixSum, mapped, output, mid, hi);
                left.fork();
                right.compute();
                left.join();
            }
        }
    }
}