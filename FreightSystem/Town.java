import java.util.ArrayList;

/**
 * Representation of town
 * @author z3460693
 */
public class Town {
	
	private String townName; 
	private int unloadingCost;
	private ArrayList<Job> edge; // List of edges from this town

	/**
	 * Constructor for the town name and unloading cost
	 * @param townName name of Town
	 * @param unloadingCost cost to Unload at this town
	 */
	public Town(String townName, int unloadingCost) {
		this.townName = townName;
		this.unloadingCost = unloadingCost;
		edge = new ArrayList<Job>();
	}
	
	/**
	 * Connection/Edge getter
	 * @param destTown from this town to other town
	 * @param travelCost cost of edge/connection to other town
	 */
	public void addEdge(Town destTown, int travelCost) {
		Job newEdge = new Job(this, destTown, travelCost);
		edge.add(newEdge);
		return;
	}

	/**
	 * List of edges/connections getter
	 * @return clone of connections from this city
	 */
	public ArrayList<Job> getEdge() {
		ArrayList<Job> cloneOfEdge = new ArrayList<Job>();
		for (Job j : edge) {
			cloneOfEdge.add(j);
		}
		return cloneOfEdge;
	}
	
	/**
	 * Town Name getter
	 * @return name of Town
	 */
	public String getName() {
		return townName;
	}
	
	/**
	 * Cost to Unload getter
	 * @return Unloading Cost of this town
	 */
	public int getUnloadingCost() {
		return unloadingCost;
	}
	
	/**
	 * Travel Cost from this town to destination town getter
	 * @param destTown other connecting town
	 * @return travel cost from this town as int >= 0
	 */
	public int getTravelCost(Town destTown) {
		if (this.equals(destTown)) {
			return 0;
		}
		for (Job j : edge) {
			if (j.getDestTown().equals(destTown)) {
				return j.getTravelCost();
			}
		}
		return 0;
	}
	
	/**
	 * Determines if Object is same as Town 
	 * @param o Object compared to this Town
	 * @return true if Object is same as Town
	 */		
	@Override
	public boolean equals(Object o) {
		// Same object
		if (this == o) return true;
		// Different class type
		if (getClass() != o.getClass()) return false;
		// Different attributes check
		Town otherTown = (Town) o;
		// Different town name
		if (!townName.equals(otherTown.townName)) return false;
		// Different unloading cost
		if (unloadingCost != otherTown.unloadingCost) return false;
		return true;
	}
	
}

