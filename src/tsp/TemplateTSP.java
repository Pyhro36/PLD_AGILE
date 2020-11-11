package tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import model.Hour;

/**
 * Class of the branch and bound template implementation of the TSP
 * @author Pierre-Louis Lefebvre
 */
public abstract class TemplateTSP extends Observable implements TSP {

	private Integer[] bestSolution;
	private int costBestSolution = 0;
	private Boolean limitTimeReached;
	protected boolean stopped;

	/**
	 * @brief base and default Constructor
	 */
	public TemplateTSP() {
		stopped = true;
		limitTimeReached = false;
		costBestSolution = Integer.MAX_VALUE;
	}
	
	@Override
	public Boolean getLimitTimeReached() {
		return limitTimeReached;
	}

	@Override
	public void searchSolution(int limitTime, int nodesNbr, int[][] costs, int[] durations, Hour[] timeWindowsStarts,
			Hour[] timeWindowsEnds, Hour departureTime) {
		stopped = false;
		bestSolution = new Integer[nodesNbr];
		ArrayList<Integer> nonVus = new ArrayList<Integer>();
		for (int i = 1; i < nodesNbr; i++)
			nonVus.add(i);
		ArrayList<Integer> vus = new ArrayList<Integer>(nodesNbr);
		vus.add(0); // the first visited node is 0
		branchAndBound(0, nonVus, vus, 0, costs, durations, System.currentTimeMillis(), limitTime, timeWindowsStarts,
				timeWindowsEnds, departureTime);
		stopped = true;
	}

	@Override
	public Integer getBestSolution(int i) {
		if ((bestSolution == null) || (i < 0) || (i >= bestSolution.length))
			return null;
		return bestSolution[i];
	}

	@Override
	public int getCostBestSolution() {
		return costBestSolution;
	}

	@Override
	public boolean solutionFound() {
		return costBestSolution != Integer.MAX_VALUE;
	}

	/**
	 * @return true if the searchSolution has finished to explore all the
	 *         solutions or if the user has stopped manually the algorithm
	 */
	public boolean isStopped() {
		return stopped;
	}
	
	/**
	 * Method called to stop the research of solutions
	 */
	public void stopComputing() {
		stopped = true;
	}

	/**
	 * This method must be redefined in the under-classes of TemplateTSP
	 * 
	 * @param currentNode
	 * @param notSeen
	 *            : table of the nodes keeping not visited
	 * @param costs
	 *            : costs[i][j] = duration to go from i to j, with 0 <= i <
	 *            nodeNbr and 0 <= j < nodeNbr
	 * @param durations
	 *            : durations[i] = duration for visiting the node i, with 0 <= i
	 *            < nodeNbr
	 * @return a lower bound of the costs of the permutations beginning by
	 *         currentNode, containing each node of notSeen exactly one time and
	 *         finishing by 0
	 */
	protected abstract int bound(Integer currentNode, ArrayList<Integer> notSeen, int[][] costs, int[] durations);

	/**
	 * This method must be redefined in the under-classes of TemplateTSP
	 * 
	 * @param currentNode
	 * @param notSeen
	 *            : table of the nodes keeping not visited
	 * @param costs
	 *            : costs[i][j] = duration to go from i to j, with 0 <= i <
	 *            nodeNbr and 0 <= j < nodeNbr
	 * @param durations
	 *            : durations[i] = duration for visiting the node i, with 0 <= i
	 *            < nodeNbr
	 * @return an iterator on all the nodes of notSeen
	 */
	protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout,
			int[] duree);

	/**
	 * Method called when the algorithm find a better solution
	 * 
	 * @param seen
	 *            the list of ordered nodes which describe the solution
	 * @param costSeen
	 *            the total cost of the solution
	 */
	protected void saveBetterSolution(ArrayList<Integer> seen, int costSeen) {
		seen.toArray(bestSolution);
		costBestSolution = costSeen;
	}

	/**
	 * Method defining the template of a resolution by branch and bound of the
	 * TSP
	 * 
	 * @param currentNode
	 *            the last-visited node
	 * @param notSeen
	 *            the list of not-already-visited nodes
	 * @param seen
	 *            the list of visited node (including currentNode)
	 * @param costSeen
	 *            the costs sum of the arcs of the path passing by all the nodes
	 *            of seen + the duration sum of all the nodes of seen
	 * @param costs
	 *            : costs[i][j] = duration to go from i to j, with 0 <= i <
	 *            nodeNbr and 0 <= j < nodeNbr
	 * @param durations
	 *            : durations[i] = duration for visiting the node i, with 0 <= i
	 *            < nodeNbr
	 * @param startTime
	 *            : time at which the search begin (in seconds)
	 * @param limitTime
	 *            : time limit of the search (in seconds)
	 * @param timeAtCurrentNode
	 *            :
	 */
	private void branchAndBound(int currentNode, ArrayList<Integer> notSeen, ArrayList<Integer> seen, int costSeen,
			int[][] costs, int[] durations, long startTime, int limitTime, Hour[] timeWindowStarts,
			Hour[] timeWindowEnds, Hour timeAtCurrentNode) {

		if (System.currentTimeMillis() - startTime > limitTime) {
			limitTimeReached = true;
			return;
		}

		if (stopped) { // stopped by the user
			return;
		}

		if (notSeen.size() == 0) { // all the nodes have been visited
			costSeen += costs[currentNode][0];

			if (costSeen < costBestSolution) { // we found a better solution
												// than bestSolution
				saveBetterSolution(seen, costSeen);
			}

		} else if (costSeen + bound(currentNode, notSeen, costs, durations) < costBestSolution) {
			Iterator<Integer> it = iterator(currentNode, notSeen, costs, durations);

			while (it.hasNext()) {
				Integer nextNode = it.next();
				int costToNode = costs[currentNode][nextNode];
				Hour timeToNextNode = new Hour(timeAtCurrentNode.getTimeString());
				timeToNextNode.addTime(costToNode);

				int waitingAtNode = 0;

				if (timeWindowStarts[nextNode] != null && timeWindowEnds[nextNode] != null) { // the
																								// next
																								// node
																								// has
																								// a
																								// time
																								// window
					waitingAtNode = timeToNextNode.waitDurationToWindow(timeWindowStarts[nextNode],
							timeWindowEnds[nextNode], durations[nextNode]);
				}

				if (waitingAtNode >= 0) { // means that we can deliver or not at
											// this node in the time window
					seen.add(nextNode);
					notSeen.remove(nextNode);
					timeToNextNode.addTime(waitingAtNode + durations[nextNode]);
					branchAndBound(nextNode, notSeen, seen, costSeen + costToNode + waitingAtNode + durations[nextNode],
							costs, durations, startTime, limitTime, timeWindowStarts, timeWindowEnds, timeToNextNode);
					seen.remove(nextNode);
					notSeen.add(nextNode);
				}
			}
		}
	}
}
