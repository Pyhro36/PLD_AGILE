package tsp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implementation of the TSP that includes more than TSP2 a search of nodes in
 * ascendent order of cost
 * 
 * @author Pierre-Louis Lefebvre
 */
public class TSP3 extends TSP2 {

	@Override
	protected Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> notSeen, int[][] costs,
			int[] durations) {
		return new IteratorMinFirst(notSeen, currentNode, costs, durations);
	}

}
