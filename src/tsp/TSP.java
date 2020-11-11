package tsp;

import model.Hour;

/**
 * Interface that describes the use of the TSP algorithm for the search of a
 * delivery round
 * 
 * @author Pierre-Louis
 *
 */
public interface TSP {

	/**
	 * @return true if searchSolution() finished because the limit time had been
	 *         reached, before that all the search space was explored
	 */
	public Boolean getLimitTimeReached();

	/**
	 * @brief Search a minimal duration circuit passing by each node (between 0
	 *        and nodeNbr-1)
	 * 
	 * @param limitTime
	 *            : limit (in milliseconds) on the execution time of
	 *            searchSolution
	 * @param nodesNbr
	 *            : the number of nodes in the graph
	 * @param costs
	 *            : costs[i][j] = duration to go from i to j, with 0 <= i <
	 *            nodesNbr and 0 <= j < nodesNbr
	 * @param durations
	 *            : durations[i] = duration of visiting the node i, with 0 <= i
	 *            < nodesNbr
	 * @param timeWindowsStarts
	 *            : timeWindowsStarts[i] = the minimal time to arrive at the
	 *            node i, we can arrive before, but therefore we must wait to
	 *            this time
	 * @param timeWindowsEnds
	 *            : timeWindowsEnds[i] = the minimal time to quit at the node i,
	 *            after visiting it
	 * @param departureTime
	 *            : the hour of the departure from the first node
	 */
	public void searchSolution(int limitTime, int nodesNbr, int[][] costs, int[] durations, Hour[] timeWindowsStarts,
			Hour[] timeWindowsEnds, Hour departureTime);

	/**
	 * @param i
	 * @return the node visited in i-th place in the solution computed by
	 *         searchSolution
	 */
	public Integer getBestSolution(int i);

	/**
	 * @return the total duration of the solution computed par searchSolution
	 *         (with durations and waiting times)
	 */
	public int getCostBestSolution();

	/**
	 * @return true if the searchSolution has found a solution
	 */
	public boolean solutionFound();

}
