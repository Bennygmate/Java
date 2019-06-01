/**
 * Representation of Job
 * @author Ben C
 */
public class Job {
	
	private Town thisTown;
	private Town destTown;
	private int travelCost;
	
	/**
	 * Constructor for this town, destination town and their travel cost
	 * @param thisTown starting town for job
	 * @param destTown ending town for job
	 * @param travelCost cost for thisTown to destTown
	 */
	public Job(Town thisTown, Town destTown, int travelCost) {
		this.thisTown = thisTown;
		this.destTown = destTown;
		this.travelCost = travelCost;
	}
	
	/**
	 * Start town getter
	 * @return the starting town of job
	 */
	public Town getThisTown() {
		return thisTown;
	}
	
	/**
	 * Ending town getter
	 * @return the ending town of job
	 */
	public Town getDestTown() {
		return destTown;
	}
	
	/**
	 * Travel Cost getter
	 * @return travel cost from start to end town
	 */
	public int getTravelCost() {
		return travelCost;
	}
	
	/**
	 * Determines if Object is same as Job
	 * @param o Object compared to this Job
	 * @return true if Object is equal to Job
	 */	
	@Override
	public boolean equals(Object o) {
		// if same instance
		if (this == o) {
			return true;
		}
		// if class type is not identical
		if (getClass() != o.getClass()) {
			return false;
		}
		// if fields are not identical
		Job otherJob = (Job) o;
		if (!thisTown.equals(otherJob.thisTown)) {
			return false;
		}
		if (!destTown.equals(otherJob.destTown)) {
			return false;
		}
		if (travelCost != otherJob.travelCost) {
			return false;
		}
		return true;
	}

}
