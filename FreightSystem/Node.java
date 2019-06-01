import java.util.ArrayList;
import java.util.Comparator;

/**
 * Representation of Node for use in A* search
 * @author z3460693
 */
public class Node implements Comparator<Node> {
	
	private Town town;
	private ArrayList<Job> currPath;
	private int gCost;
	private int hEstimate; 
	
	/**
	 * Constructor for town, current path, g cost and h estimate
	 * @param town Town corresponding to Node
	 * @param currPath List of paths to get to Node
	 * @param gCost Total cost to get from initial node to this node
	 * @param hEstimate Estimate of cost to goal state
	 */
	public Node(Town town, ArrayList<Job> currPath, int gCost, int hEstimate) {
		this.town = town;
		this.currPath = currPath;
		this.gCost = gCost;
		this.hEstimate = hEstimate;
	}
	
	/**
	 * Town getter
	 * @return Town of this Node
	 */
	public Town getTown() {
		return town;
	}

	/**
	 * Current Path getter
	 * @return cloned path to this Node as ArrayList
	 */
	public ArrayList<Job> getCurrPath() {
		ArrayList<Job> pathClone = new ArrayList<Job>();
		for (Job j : currPath) {
			pathClone.add(j);
		}
		return pathClone;
	}

	/**
	 * g(n) getter
	 * @return g(n) of this Node
	 */
	public int getGCost() {
		return gCost;
	}

	/**
	 * f(n) getter
	 * @return f(n) of this Node
	 */
	public int getFCost() {
		return gCost + hEstimate;
	}
	
	/**
	 * Compares ordering of nodes, fromComparator implementation.
	 * @param node current Node
	 * @param frontierNode comparison Node
	 * @return positive int when f(n) > f(n'), negative int when f(n) < f(n'), 0 when f(n) = f(n')
	 */
	@Override
	public int compare(Node node, Node frontierNode) {
		return (node.getFCost() - frontierNode.getFCost());
	}
	
	/**
	 * Determines if Object is same as Node and higher f(n) 
	 * @param o Object compared to this Node
	 * @return true if Object is same as Node and f(n') > f(n).
	 */	
	@Override
	public boolean equals(Object o) {
		// Same object
		if (this == o) return true; 
		// Different class type
		if (getClass() != o.getClass()) return false; 
		// Different attributes check
		Node frontierNode = (Node) o;
		// Different town
		if (!town.equals(frontierNode.town)) return false;
		// f(n) < f(n')
		if (gCost + hEstimate >= frontierNode.gCost + frontierNode.hEstimate) return false;
		return true;
	}
	
}