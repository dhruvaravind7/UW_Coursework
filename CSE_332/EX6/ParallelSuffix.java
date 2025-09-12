import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ParallelSuffix {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    public static int[] suffixSum(int[] arr, int cutoff) {
        ParallelSuffix.CUTOFF = cutoff;
        Node root = POOL.invoke(new BuildTreeTask(arr, 0, arr.length));
        int[] suffixSum = new int[arr.length];
        POOL.invoke(new PopulateSuffixSum(root, 0, suffixSum, arr));
        return suffixSum;
    }

    private static class PopulateSuffixSum extends RecursiveAction {
        private Node node;
        private int rightSum;
        private int[] suffixSum;
        private int[] arr;

        public PopulateSuffixSum(Node node, int rightSum, int[] suffixSum, int[] arr) {
            this.node = node;
            this.rightSum = rightSum;
            this.suffixSum = suffixSum;
            this.arr = arr;
        }

        public void compute() {
            node.rightSum = rightSum;
            if (node.leftChildNode == null && node.rightChildNode == null) {
                int runningSum = node.rightSum;
                for (int i = node.hi - 1; i >= node.lo; i--) {
                    runningSum += arr[i];
                    suffixSum[i] = runningSum;
                }
            } else {
                PopulateSuffixSum leftTask = new PopulateSuffixSum(node.leftChildNode, rightSum + node.rightChildNode.sum, suffixSum, arr);
                PopulateSuffixSum rightTask = new PopulateSuffixSum(node.rightChildNode, rightSum, suffixSum, arr);
                leftTask.fork();
                rightTask.compute();
                leftTask.join();
            }
        }
    }

    private static class BuildTreeTask extends RecursiveTask<Node> {
        private int[] input;
        private int lo;
        private int hi;

        public BuildTreeTask(int[] input, int lo, int hi) {
            this.input = input;
            this.lo = lo;
            this.hi = hi;
        }

        public Node compute() {
            if (hi - lo <= ParallelSuffix.CUTOFF) {
                int sum = 0;
                for (int i = lo; i < hi; i++) {
                    sum += input[i];
                }
                return new Node(lo, hi, sum);
            } else {
                int mid = (lo + hi) / 2;
                BuildTreeTask leftTask = new BuildTreeTask(input, lo, mid);
                BuildTreeTask rightTask = new BuildTreeTask(input, mid, hi);
                leftTask.fork();
                Node right = rightTask.compute();
                Node left = leftTask.join();
                int sum = left.sum + right.sum;
                return new Node(lo, hi, sum, left, right);
            }
        }
    }
}