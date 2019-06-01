import java.util.ArrayList;

/**
 * Representation of a heuristic for A* search algorithm
 * @author Ben C
 */
public class HeuristicB implements StrategyHeuristic {
	/**
	 * Calculates estimated cost to destTown goal using heuristic B
	 * Admissible because it does not include unloading cost (relaxed problem)
	 * @param requiredTrips the list of required Trips within the TripPlanner.
	 * @param jobList list of jobs required
	 * @param currPath the path taken for the node
	 * @param destTown town to which next trip is
	 * @return integer >= 0 representing the estimated cost from current state to goal state
	 */
	@Override
	public int calculateHCost(ArrayList<Job> jobList, ArrayList<Job> currPath, Town destTown) { // O(n^3)
		int heuristicCost = 0;
		boolean firstSearch = true;
		int minCost = 0;
		// checks each Job in Job List not in the current path 
		for (Job j : jobList) { // O(n)
			if (!currPath.contains(j)) { // O(n)
				// add travel cost only
				heuristicCost += j.getTravelCost(); 
				//+ j.getThisTown().getUnloadingCost(); // O(1) --> Faster heuristic (not sure if admissible)
				if (firstSearch) { // find minCost to the connecting towns
					minCost = destTown.getTravelCost(j.getThisTown()); // getTravelCost is O(n)
					firstSearch = false;
				} else { // find minCost to the connecting towns
					if (destTown.getTravelCost(j.getThisTown()) < minCost) { // O(n)
						minCost = destTown.getTravelCost(j.getThisTown()); // O(n)
					}
				}
			}
		}
		return heuristicCost + minCost;
	}
	
}
