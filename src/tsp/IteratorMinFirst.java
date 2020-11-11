package tsp;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class of an Iterator on a collection of nodes described by their Indices
 * (Integer) that iter on this collection by ascendant order of cost from a
 * given node + duration
 * 
 * @author Pierre-Louis
 *
 */

public class IteratorMinFirst implements Iterator<Integer> {

	LinkedList<Integer> candidates;
	Iterator<Integer> it;

	/**
	 * @brief Base Constructor
	 * @param notSeen
	 *            the Collection of node
	 * @param currentNode
	 *            the given node from wich we take the costs
	 * @param costs
	 *            the tab of costs ordered as the same order that the Collection
	 * @param durations
	 *            the tab of durations of each node, ordered as the same order
	 *            than the Collection
	 */
	public IteratorMinFirst(Collection<Integer> notSeen, int currentNode, int[][] costs, int[] durations) {
		candidates = new LinkedList<Integer>();
		candidates.addAll(notSeen);

		candidates.sort(new Comparator<Integer>() {
			public int compare(Integer i1, Integer i2) {
				return ((durations[i1] + costs[currentNode][i1]) - (durations[i2] + costs[currentNode][i2]));
			}
		});

		it = candidates.iterator();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public Integer next() {
		return it.next();
	}
}
