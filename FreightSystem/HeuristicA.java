import java.util.ArrayList;

/**
 * Representation of a heuristic (zero) for A* search algorithm
 * @author Ben C
 */
public class HeuristicA implements StrategyHeuristic{
	
	/**
	 * Calculates estimated cost to destTown goal using heuristic A
	 * @param requiredTrips the list of required Trips within the TripPlanner.
	 * @param jobList list of jobs required
	 * @param currPath the path taken for the node
	 * @param destTown town to which next trip is
	 * @return integer 0
	 */
	@Override
	public int calculateHCost(ArrayList<Job> jobList, ArrayList<Job> currPath, Town destTown) {
		return 0;
	}
	
}
