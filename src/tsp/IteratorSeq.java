package tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * Class of an sequential (same order) iterator on a collection of node
 * described by their Indices (Integer)
 * 
 * @author Pierre-Louis
 *
 */
public class IteratorSeq implements Iterator<Integer> {

	protected Integer[] candidates;
	protected int candidatesNbr;

	/**
	 * @brief Base Constructor
	 * @param notSeen
	 *            the collection of node (Integer)
	 */
	public IteratorSeq(Collection<Integer> notSeen) {
		this.candidates = new Integer[notSeen.size()];
		candidatesNbr = 0;
		for (Integer s : notSeen) {
			candidates[candidatesNbr++] = s;
		}
	}

	@Override
	public boolean hasNext() {
		return candidatesNbr > 0;
	}

	@Override
	public Integer next() {
		return candidates[--candidatesNbr];
	}

	@Override
	public void remove() {
	}

}
