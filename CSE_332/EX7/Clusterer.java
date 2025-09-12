import java.util.*;

public class Clusterer {
    private List<List<WeightedEdge<Integer, Double>>> adjList; // the adjacency list of the original graph
    private List<List<WeightedEdge<Integer, Double>>> mstAdjList; // the adjacency list of the minimum spanning tree
    private List<List<Integer>> clusters; // a list of k points, each representing one of the clusters.
    private double cost; // the distance between the closest pair of clusters

    public Clusterer(double[][] distances, int k){
        int n = distances.length;

        adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }
            
        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                if (distances[row][col] != 0) {
                    adjList.get(row).add(new WeightedEdge<>(row, col, distances[row][col]));
                    adjList.get(col).add(new WeightedEdge<>(col, row, distances[row][col]));
                }
            }
        }

        mstAdjList = new ArrayList<>();
        clusters = new ArrayList<>();

        prims(0);
        makeKCluster(k);
    }

    private void prims(int start) {
        int n = adjList.size();

        mstAdjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mstAdjList.add(new ArrayList<>(Math.max(4, n / 10)));
        }

        boolean[] inMST = new boolean[n];  
        double[] key = new double[n];      
        //int[] parent = new int[n];         

        Arrays.fill(key, Double.POSITIVE_INFINITY);
        //Arrays.fill(parent, -1);
        key[start] = 0;

        BinaryMinHeap<WeightedEdge<Integer, Double>> minHeap = new BinaryMinHeap<>();

        inMST[start] = true;
        for (WeightedEdge<Integer, Double> edge : adjList.get(start)) {
            key[edge.destination] = edge.weight;
            //parent[edge.destination] = start;
            minHeap.insert(edge);
        }

        while (!minHeap.isEmpty()) {
            WeightedEdge<Integer, Double> edge = minHeap.extract();
            int u = edge.source;
            int v = edge.destination;

            if (inMST[v]) continue;

            mstAdjList.get(u).add(edge);
            mstAdjList.get(v).add(new WeightedEdge<>(v, u, edge.weight));
            inMST[v] = true;

            for (WeightedEdge<Integer, Double> next : adjList.get(v)) {
                int neighbor = next.destination;
                if (!inMST[neighbor] && next.weight < key[neighbor]) {
                    key[neighbor] = next.weight;
                    //parent[neighbor] = v;
                    minHeap.insert(next);  
                }
            }
        }
    }

    private void makeKCluster(int k){
        List<WeightedEdge<Integer, Double>> edges = new ArrayList<>();

        for (int source = 0; source < mstAdjList.size(); source++) {
            for (WeightedEdge<Integer, Double> edge : mstAdjList.get(source)) {
                int destination = edge.destination;
                if (source < destination) {
                    edges.add(edge);
                }
            }

        }

        edges.sort((a, b) -> Double.compare(b.weight, a.weight));

        Set<String> removedEdges = new HashSet<>();
        for (int i = 0; i < k - 1; i++) {
            WeightedEdge<Integer, Double> edge = edges.get(i);
            cost = edge.weight;  
            int u = edge.source;
            int v = edge.destination;
            String id = u + "-" + v;
            removedEdges.add(id);
        }

        boolean[] visited = new boolean[adjList.size()];
        clusters = new ArrayList<>();

        for (int i = 0; i < adjList.size(); i++) {
            if (!visited[i]) {
                List<Integer> cluster = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);
                visited[i] = true;

                while (!queue.isEmpty()) {
                    int node = queue.poll();
                    cluster.add(node);

                    for (WeightedEdge<Integer, Double> edge : mstAdjList.get(node)) {
                        int neighbor = edge.destination;
                        String id = Math.min(node, neighbor) + "-" + Math.max(node, neighbor);
                        if (!removedEdges.contains(id) && !visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.add(neighbor);
                        }
                    }
                }

                clusters.add(cluster);
            }
        }

    }

    public List<List<Integer>> getClusters(){
        return clusters;
    }

    public double getCost(){
        return cost;
    }

}
