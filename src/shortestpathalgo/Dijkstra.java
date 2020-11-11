package shortestpathalgo;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class who describe the algorithm of Dijkstra applied on a graph from a chosen
 * node of it
 * 
 * @author Pierre-Louis Lefebvre
 */
public class Dijkstra {
	private Graph graph;
	private Integer departure;
	private Map<Integer, Integer> predecessors;
	private Map<Integer, Integer> distances;
	private Set<Integer> completedNodes;
	private Set<Integer> visitedNodes;

	/**
	 * @brief Base constructor of the Dijkstra calculator
	 * 
	 * @param graph
	 *            the graph on which the algorithm will be applied
	 * @param departure
	 *            the indice of the departure node for the algorithm
	 */
	public Dijkstra(Graph graph, Integer departure) {
		this.graph = graph;
		this.departure = departure;
		this.predecessors = new HashMap<Integer, Integer>();
		this.distances = new HashMap<Integer, Integer>();
		this.completedNodes = new HashSet<Integer>();
		this.visitedNodes = new HashSet<Integer>();
	}

	/**
	 * compute the Dijkstra algorithm
	 */
	public void compute() {

		distances.put(departure, 0);
		int minimalDistanceVisited = 0;
		visitedNodes.add(departure);

		while (!visitedNodes.isEmpty()) {
			minimalDistanceVisited = getMinimalDistanceVisited();
			List<Integer> successors = graph.getSuccessors(minimalDistanceVisited);

			for (int successor : successors) {
				if (!completedNodes.contains(successor)) {
					graph.release(minimalDistanceVisited, successor, predecessors, distances);

					if (!visitedNodes.contains(successor)) {
						visitedNodes.add(successor);
					}
				}
			}

			visitedNodes.remove(minimalDistanceVisited);
			completedNodes.add(minimalDistanceVisited);
		}
	}

	/**
	 * @brief gives the predecessor of node given in parameter after calling
	 *        compute()
	 * @param node
	 *            the indice of the node
	 * @return the indice of the predecessor
	 */
	public Integer getPredecessor(Integer node) {

		if (predecessors.containsKey(node)) {
			return predecessors.get(node);
		}

		throw new NullPointerException("no predecessor for " + node + " indice in map");
	}

	/**
	 * @brief give the distance from the departure to the others nodes of the
	 *        graph after calling compute()
	 * @param node
	 *            : the other node of the graph
	 * @return the cost of the path
	 */
	public Integer getDistance(Integer node) {

		if (distances.containsKey(node)) {
			return distances.get(node);
		}

		throw new NullPointerException("no distance computed for " + node + " indice in map");
	}

	/**
	 * @brief return the visited node of minimal distance from the departure
	 * @return the indice of the node
	 */
	private Integer getMinimalDistanceVisited() {

		Integer minimalDistanceVisited = null;

		if (visitedNodes.contains(departure)) {
			minimalDistanceVisited = departure;

		} else {
			for (Integer node : visitedNodes) {

				if (distances.get(node) != null) { // distance of node isn't
													// infinite
					if (minimalDistanceVisited == null) {
						minimalDistanceVisited = node; // if a minimal distance
														// visited node ins't
														// already defined
					} else if (distances.get(node) < distances.get(minimalDistanceVisited)) {
						minimalDistanceVisited = node;
					}
				}
			}
		}

		return minimalDistanceVisited;
	}
}
