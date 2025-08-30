// Dhruv Aravind
// 11/12/2024
// CSE 123
// P2: Disaster Relief
// TA: Laura Khotemlyansky

import java.util.*;

// Client class that represents what the User wants to run.
public class Client {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {
        //List<Region> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Region> scenario = createSimpleScenario();
        System.out.println(scenario);
        
        double budget = 2000;
        Allocation allocation = allocateRelief(budget, scenario);
        printResult(allocation, budget);
    }

    /* Behavior:
     *  - Returns an allocation of regions in the order which aid should be provided to maximize 
     *    the number of people being saved. If the number of people being saved is the same it 
     *    returns the solution that costs less money. 
     * Parameters:
     *  - budget: The amount of money we have to spend.
     *            Cannot be negative.
     *  - sites: A list of regions that have been hit by a disaster and need help.
     *           Must be non-null 
     * Returns:
     *  - Allocation: The allocation of what regions should get funded and in what order. 
     * Exceptions:
     *  - N/A 
     */
    public static Allocation allocateRelief(double budget, List<Region> sites) { 
        return (allocateRelief(new Allocation(), budget, sites));

    }

    /* Behavior: 
     * - Private recursive method that explores all combinations of regions to maximize the 
     *   number of people in the final alloction. If the number of people being saved is the same it 
     *    returns the solution that costs less money.
     * Parameters:
     *  - allo: Allocation object that contains the current allocation of relief resources. 
     *          Must be non-null
     *  - budget: The remaining budget for allocating relief. 
     *            Cannot be negative.
     *  - sites: The list of regions to consider for allocating relief. 
     *           Must be non-null
     * Returns:
     *  - Allocation: An Allocation object that represents the best allocation of relief 
     *                resources found within the budget.
     * Exceptions:
     *  - N/A
     */
    private static Allocation allocateRelief(Allocation allo, double budget, List<Region> sites) {
        Allocation greatestAllo = allo;
        for (Region region : sites) {
            if (!allo.getRegions().contains(region)) {
                if (allo.withRegion(region).totalCost() - allo.totalCost() <= budget) {
                    Allocation newAllo = allocateRelief(allo.withRegion(region), 
                                         budget - (allo.withRegion(region).totalCost() - 
                                         allo.totalCost()), sites);

                    if (newAllo.totalPeople() > greatestAllo.totalPeople() || (newAllo.totalPeople() == greatestAllo.totalPeople() && 
                    newAllo.totalCost() < greatestAllo.totalCost())) {
                        greatestAllo = newAllo;
                    // } else if (newAllo.totalPeople() == greatestAllo.totalPeople() && 
                    //            newAllo.totalCost() < greatestAllo.totalCost()){
                    //     greatestAllo = newAllo;
                    }
                }
            }

        }
        return (greatestAllo);
    }

    ///////////////////////////////////////////////////////////////////////////
    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ///////////////////////////////////////////////////////////////////////////
    
    /**
    * Prints each allocation in the provided set. Useful for getting a quick overview
    * of all allocations currently in the system.
    * @param allocations Set of allocations to print
    */
    public static void printAllocations(Set<Allocation> allocations) {
        System.out.println("All Allocations:");
        for (Allocation a : allocations) {
            System.out.println("  " + a);
        }
    }

    /**
    * Prints details about a specific allocation result, including the total people
    * helped, total cost, and any leftover budget. Handy for checking if we're
    * within budget limits!
    * @param alloc The allocation to print
    * @param budget The budget to compare against
    */
    public static void printResult(Allocation alloc, double budget) {
        System.out.println("Result: ");
        System.out.println("  " + alloc);
        System.out.println("  People helped: " + alloc.totalPeople());
        System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
        System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));
    }

    /**
    * Creates a scenario with numRegions regions by randomly choosing the population 
    * and cost of each region.
    * @param numLocs Number of regions to create
    * @param minPop Minimum population per region
    * @param maxPop Maximum population per region
    * @param minCostPer Minimum cost per person
    * @param maxCostPer Maximum cost per person
    * @return A list of randomly generated regions
    */
    public static List<Region> createRandomScenario(int numLocs, int minPop, int maxPop,
                                                    double minCostPer, double maxCostPer) {
        List<Region> result = new ArrayList<>();

        for (int i = 0; i < numLocs; i++) {
            int pop = RAND.nextInt(minPop, maxPop + 1);
            double cost = RAND.nextDouble(minCostPer, maxCostPer) * pop;
            result.add(new Region("Region #" + i, pop, round2(cost)));
        }

        return result;
    }

    /**
    * Manually creates a simple list of regions to represent a known scenario.
    * @return A simple list of regions
    */
    public static List<Region> createSimpleScenario() {
        List<Region> result = new ArrayList<>();

        result.add(new Region("Region #1", 50, 500));
        result.add(new Region("Region #2", 100, 700));
        result.add(new Region("Region #3", 60, 1000));
        result.add(new Region("Region #4", 20, 1000));
        result.add(new Region("Region #5", 200, 900));
        return result;
    }    

    /**
    * Rounds a number to two decimal places.
    * @param num The number to round
    * @return The number rounded to two decimal places
    */
    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}
