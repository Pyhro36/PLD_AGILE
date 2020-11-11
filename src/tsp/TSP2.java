package tsp;

import java.util.ArrayList;

/**
 * Implementation of the TSP that includes a better fonction bound (lower bound
 * of costs of not seen nodes)
 * 
 * @author Pierre-Louis Lefebvre
 *
 */
public class TSP2 extends TSP1 {

	@Override
	protected int bound(Integer currentNode, ArrayList<Integer> notSeen, int[][] costs, int[] durations) {
		int bound = (notSeen.isEmpty()) ? 0 : minCostFromANode(currentNode, costs[currentNode], durations, notSeen);

		for (Integer ns : notSeen) {
			bound += minCostFromANode(ns, costs[ns], durations, notSeen);
		}

		return bound;
	}

	/**
	 * @param aNode
	 *            a not seen node
	 * @param costs
	 *            : costs[i] is the cost of the arc from aNode to the not seen
	 *            node i (defined by branchAndBound in TemplateTSP)
	 * @param durations
	 *            : durations[i] is the duration of the delivery at the node i
	 * @param notSeen
	 *            is the List of not seen node the TSP algorithm
	 * @return the minimal cost + duration of the next from the node aNode
	 */
	private int minCostFromANode(Integer aNode, int[] costs, int[] durations, ArrayList<Integer> notSeen) {
		int minCost = (aNode.equals(0)) ? Integer.MAX_VALUE : costs[0]; // re-bound
																		// to 0

		for (Integer ns : notSeen) {
			if (!(ns.equals(aNode)) && minCost > (costs[ns] + durations[ns])) {
				minCost = costs[ns] + durations[ns];
			}
		}

		return minCost;
	}
}
