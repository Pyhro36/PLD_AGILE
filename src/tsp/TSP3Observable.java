package tsp;

import java.util.ArrayList;

/**
 * Class that make the TSP Observable to notify the map when a new solution is
 * found
 * 
 * @author Pierre-Louis Lefebvre
 */
public class TSP3Observable extends TSP3 {

	@Override
	protected void saveBetterSolution(ArrayList<Integer> seen, int costSeen) {
		super.saveBetterSolution(seen, costSeen);
		setChanged();
		notifyObservers(new Object());
	}
}
