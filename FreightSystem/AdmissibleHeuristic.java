import java.util.ArrayList;

/**
 * Representation of AdmissibleHeuristic as a strategy pattern implementation
 * @author z3460693
 */
public class AdmissibleHeuristic {
	private StrategyHeuristic heuristic;
	
	/**
	 * Constructor allows selection of heuristic strategy
	 * @param heuristic strategy to be chosen
	 */
	public AdmissibleHeuristic(StrategyHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * Runs the heuristic estimation using the chosen heuristic strategy
	 * @param jobList list of jobs required
	 * @param currPath the path taken for the node
	 * @param destTown town to which next trip is
	 * @return
	 */
	public int runHeuristic(ArrayList<Job> jobList, ArrayList<Job> currPath, Town destTown) {
		return heuristic.calculateHCost(jobList, currPath, destTown);
	}
}