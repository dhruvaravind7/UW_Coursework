import java.util.*;

public class DonorGraph {
    private List<List<Match>> adjList;

    // The donatingTo array indicates which repient each donor is
    // affiliated with. Specifically, the donor at index i has volunteered
    // to donate a kidney on behalf of recipient donatingTo[i].
    // The matchScores 2d array gives the match scores associated with each
    // donor-recipient pair. Specificically, matchScores[x][y] gives the
    // HLA score for donor x and reciplient y.
    public DonorGraph(int[] donorToBenefit, int[][] matchScores){
        adjList = new ArrayList<>();
        for (int i = 0; i < matchScores[0].length; i++){
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < matchScores.length; i++){    
            int beneficiary = donorToBenefit[i];
            for (int j = 0; j < matchScores[i].length; j++){
                if (matchScores[i][j] >= 60) {
                    boolean contained = false;
                    for (Match donation : adjList.get(beneficiary)) {
                        if (donation.beneficiary == beneficiary && donation.recipient == j){
                            contained = true;
                        }
                    }
                    if (contained == false) {
                        adjList.get(beneficiary).add(new Match(i, beneficiary, j));
                    }
                }
            }
        }

    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public boolean isAdjacent(int start, int end){
        for(Match m : adjList.get(start)){
            if(m.recipient == end)
                return true;
        }
        return false;
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public int getDonor(int beneficiary, int recipient){
        for(Match m : adjList.get(beneficiary)){
            if(m.recipient == recipient)
                return m.donor;
        }
        return -1;
    }


    // returns a chain of matches to make a donor cycle
    // which includes the given recipient.
    // Returns an empty list if no cycle exists. 
    public List<Match> findCycle(int recipient){
        ArrayList tracker = new ArrayList<Match>();
        int[] visited = new int[adjList.size()];
        findCycle(adjList, recipient, visited, recipient, tracker);
        return tracker;
        
    }

    // Private recursive function that creates the path of a cycle. Empty list is created if there is no cycle
    // Returns whether a cycle with the recipient exists.
    private boolean findCycle(List<List<Match>> adjList, int curr, int[] visited, int recipient, ArrayList<Match> tracker) {
        visited[curr] = 1;
        boolean cycleFound = false;
        for (Match neighbor : adjList.get(curr)) {
            int v = neighbor.recipient;
            if (visited[v] == 1 && v == recipient) {
                tracker.add(neighbor);
                cycleFound = true;
            }
            else if (visited[v] == 0 && !cycleFound) {
                tracker.add(neighbor);
                cycleFound = findCycle(adjList, v, visited, recipient, tracker);
            }

        }
        visited[curr] = 2;
        if (tracker.size() > 0 && !cycleFound) {
            tracker.remove(tracker.size()-1);
        }
        return (cycleFound);
    }

    // Private recursive function that returns true if a cycle containing the recipient exists.
    private boolean hasCycle(List<List<Match>> adjList, int curr, int[] visited, int recipient) {
        visited[curr] = 1;
        boolean cycleFound = false;
        for (Match neighbor : adjList.get(curr)) {
            int v = neighbor.recipient;
            if (visited[v] == 1 && v == recipient) {
                cycleFound = true;
            }
            else if (visited[v] == 0 && !cycleFound) {
                cycleFound = hasCycle(adjList, v, visited, recipient);
            }

        }
        visited[curr] = 2;
        return (cycleFound);

    }

    // returns true or false to indicate whether there
    // is some cycle which includes the given recipient.
    public boolean hasCycle(int recipient){
        int[] visited = new int[adjList.size()];
        return (hasCycle(adjList, recipient, visited, recipient));
    }
}
