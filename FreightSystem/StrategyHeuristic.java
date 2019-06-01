import java.util.ArrayList;

/**
 * Representation of StrategyHeuristic as a strategy pattern interface
 * @author z3460693
 */
public interface StrategyHeuristic {
	
	/**
	 * Calculates H estimate cost using chosen heuristic
	 * @param jobList list of jobs required
	 * @param currPath the path taken for the node
	 * @param destTown town to which next trip is
	 * @return integer >= 0, estimating the cost of current state to goal state
	 */
	public int calculateHCost(ArrayList<Job> jobList, ArrayList<Job> currPath, Town destTown);
	
}