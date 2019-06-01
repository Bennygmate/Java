import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Representation of FreightSystem.
 * This system uses A* search algorithm to find path of lowest cost using heuristic strategy 
 * Heuristic Runtime Analysis:
 * HeuristicA: Time: O(1) because it employs heuristic cost 0
 *             Space: O(1) because it employs heuristic cost 0
 * 
 * HeuristicB: Time: O(n^3) because the for the job iterator O(n), path iterator is O(n), 
 *                          and the travel cost getter in Town is also O(n).
 *                  Space: O(1) as no variables declared within loops
 * @author Ben c
 */

public class FreightSystem {
	
	private ArrayList<Town> towns; // ArrayList of towns in FreightSystem
	private ArrayList<Job> jobList; // ArrayList of Jobs to be completed in FreightSystem
	private ArrayList<Job> successPath; // ArrayList of the successful job path 	
	private PriorityQueue<Node> frontierNode; // Priority Queue for frontier Nodes
	private ArrayList<Node> expandedNode; // ArrayList of expanded Nodes
	private int nodesExpanded; // Number of nodes expanded during A* search
	private int totalCost; // Total cost of the successful path
	
	/**
	 * Main function
	 * @pre single argument according to name of input file
	 * @post quit without errors or exceptions
	 * @param args arguments passed in
	 */
	public static void main(String[] args) {
		FreightSystem planner = new FreightSystem();
		planner.readInput(args); // read the file
		// Heuristic Strategy to implement in A Star Search
		/*		
 		* If want to employ zero heuristic uncomment this
 		AdmissibleHeuristic heuristicStrategyA = new AdmissibleHeuristic(new HeuristicA());
		if (planner.aStarSearch(heuristicStrategyA) != null) {
			planner.printSolve(); // print output
		} else {
			planner.printNoSolve();
		}
		 */
		AdmissibleHeuristic heuristicStrategyB = new AdmissibleHeuristic(new HeuristicB());
		if (planner.aStarSearch(heuristicStrategyB) != null) {
			planner.printSolve(); // print output
		} else {
			planner.printNoSolve();
		}
		
	}

	/**
	 * Constructor creates new FreightSystem.
	 */
	public FreightSystem() {
		towns = new ArrayList<Town>();
		jobList = new ArrayList<Job>();
		successPath = null;
		frontierNode = new PriorityQueue<Node>(11, new Node(null, null, 0, 0));
		expandedNode = new ArrayList<Node>();
		nodesExpanded = 0;
	}
	
	
	/**
	 * Reads then processes input from file
	 * @param input array of strings
	 */
	private void readInput(String[] input) {
		Scanner sc = null;
		try {
			sc = new Scanner(new FileReader(input[0]));
			processInput(sc);
		} catch (FileNotFoundException e) { 
		} finally {
			if (sc != null) sc.close();
		}
	}
	
	/**
	 * Processes input, adds town then edges then jobs
	 * @param sc the scanner breaking file up
	 */
	private void processInput(Scanner sc) {
		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" "); // splits by space
			if (line[0].equals("Unloading")) { // Unloading <cost> <town>
				int unloadingCost = Integer.parseInt(line[1]);
				String townName = line[2];
				Town newTown = new Town(townName, unloadingCost); // Create town
				towns.add(newTown); // Add town
			} else if (line[0].equals("Cost")) { // Cost <cost> <town1> <town2>
				int travelTime = Integer.parseInt(line[1]);
				Town TownOne = null;
				Town TownTwo = null;
				for (Town t : towns) { // find Town objects by string name
					if (t.getName().equals(line[2])) {
						TownOne = t;
					} else if (t.getName().equals(line[3])) {
						TownTwo = t;
					}
				}
				TownOne.addEdge(TownTwo, travelTime); //Add connection
				TownTwo.addEdge(TownOne, travelTime); //Add connection
			} else if (line[0].equals("Job")) { // Job <town1> <town2>
				Town thisTown = null;
				Town destTown = null;
				for (Town c : towns) { // find Town objects by string name
					if (c.getName().equals(line[1])) {
						thisTown = c;
					} else if (c.getName().equals(line[2])) {
						destTown = c;
					}
				}
				int travelCost = thisTown.getTravelCost(destTown); // get travel cost
				Job newJob = new Job(thisTown, destTown, travelCost); // create new Job
				jobList.add(newJob); // add to Jobs list
			}
		}
	}
	
	/**
	 * A* Search algorithm according to own representation
	 * @return sucessPath if found, otherwise null if no solution
	 */
	private ArrayList<Job> aStarSearch(AdmissibleHeuristic heuristicStrategy) {
		// If one of the job has no edge
		for (Job j : jobList) {
			ArrayList<Job> checkEdge = j.getThisTown().getEdge();
			if (!checkEdge.contains(j)) {
				return null;
			}
		}
		
		Town startTown = null;
		// find starting Sydney Town object
		for (Town c : towns) {
			if (c.getName().equals("Sydney")) {
				startTown = c;
				break;
			}
		}
		// Put starting Sydney node on the frontier Priority Queue
		Node firstNode = new Node(startTown, new ArrayList<Job>(), 0, 0);
		frontierNode.add(firstNode);
		
		ArrayList<Job> currPath = null; // Path to current node
		// While there are still frontier nodes to search
		while (frontierNode.peek() != null) { 
			// Remove frontier node from head of queue
			Node tempNode = frontierNode.poll();
			// Mark node as expanded
			expandedNode.add(tempNode);	
			
			// If path to node contains all job completed, goal state reached
			currPath = tempNode.getCurrPath();
			boolean goalNode = true;
			for (Job j : jobList) {
				if (!currPath.contains(j)) { //if it does not contain the job
						goalNode = false;
						break;
				}
			}
			if (goalNode) {
				totalCost = tempNode.getGCost(); //Get Cost
				nodesExpanded = expandedNode.size(); //Get expanded node count
				successPath = currPath; //This is the successful path
				return successPath;
			}
			
			Town tempTown = tempNode.getTown(); // get Town within Node
			int currGCost = tempNode.getGCost(); // get current cost within Node
			ArrayList<Job> connectedTown = tempTown.getEdge(); // get the edges from Town
			
			for (Job e1 : connectedTown) { // For each connection from current town
				Town toTown = e1.getDestTown(); // get the town to search next
				// New path to new dest
				ArrayList<Job> newPath = new ArrayList<Job>();
				for (Job j : currPath) {
					newPath.add(j);
				}
				newPath.add(e1);
				
				// Calculate new f cost
				int tmpGCost = 0;
				// Admissible heuristic strategy employed
				int tmpHCost = heuristicStrategy.runHeuristic(jobList, newPath, toTown);
				int tmpFCost = 0;
				if (jobList.contains(e1)) {
					tmpGCost = currGCost + e1.getTravelCost() + toTown.getUnloadingCost();
				} else {
					tmpGCost = currGCost + e1.getTravelCost();
				}
				tmpFCost = tmpGCost + tmpHCost;
				// Update cost if new f(n') is less than the current cost
				if (expandedNode.contains(toTown)) { // If new node in expanded list
					for (Node s : expandedNode) {
						if (s.getTown().equals(toTown)) {
							if (tmpFCost < s.getFCost()) { //f(new) < f(old)
								s = null;
								Node newNode = new Node(toTown, newPath, tmpGCost, tmpHCost);
								frontierNode.add(newNode);
							}
						}
					}
				} else if (frontierNode.contains(toTown)) { // If new node in frontier queue
					for (Node s : frontierNode) {
						if (s.getTown().equals(toTown)) {
							if (tmpFCost < s.getFCost()) { //f(new) < f(old)
								s = null;
								Node newNode = new Node(toTown, newPath, tmpGCost, tmpHCost);
								frontierNode.add(newNode);
							}
						}
					}
				} else if (!frontierNode.contains(toTown)) { // If not on the frontier queue
					Node newNode = new Node(toTown, newPath, tmpGCost, tmpHCost);
					frontierNode.add(newNode);
				}
				
			}
		}
		nodesExpanded = expandedNode.size(); //Get expanded node count
		return null;
	}

	/**
	 * Prints number of nodes expanded, the total cost of the success path, and the list of Job paths taken
	 */
	private void printSolve() {
		System.out.println(nodesExpanded + " nodes expanded");
		System.out.println("cost = " + totalCost);
		for (Job j : successPath) {
			if (jobList.contains(j)) {
				System.out.println("Job " + j.getThisTown().getName() + " to " + j.getDestTown().getName());
			} else {
				System.out.println("Empty " + j.getThisTown().getName() + " to " + j.getDestTown().getName());
			}
		}
		return;
	}
	
	/**
	 * Prints number of nodes expanded followed by no solution found
	 */
	private void printNoSolve() {
		System.out.println(nodesExpanded + " nodes expanded");
		System.out.println("No solution");
	}
	

	
}
