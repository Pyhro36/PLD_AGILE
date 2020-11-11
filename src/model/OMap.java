package model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javafx.util.Pair;
import model.Delivery;
import model.DeliveryRound;
import model.Route;
import model.Section;
import shortestpathalgo.Dijkstra;
import shortestpathalgo.Graph;
import tsp.TSP3Observable;
import tsp.TemplateTSP;

/**
 * Class OMap, corresponding to the map, the deliveryOrder, the deliveryRound
 * that we need to display information about the delivery
 * 
 * @authors Joachim Dorel
 * @authors Pierre-Louis Lefebvre
 *
 */
public class OMap extends Observable implements Graph, Observer {
	private DeliveryOrder deliveryOrder;
	private DeliveryRound deliveryRound;
	private Map<Integer, Intersection> intersections;
	private Map<Pair<Integer, Integer>, Section> sections;
	private float maxXIntersection;
	private float maxYIntersection;
	private float minXIntersection;
	private float minYIntersection;
	private TemplateTSP tsp;
	private Route[][] possibleRoutes;
	private Delivery selectedDelivery;
	private int selectedDeliveryIndex;
	private Intersection selectedIntersection;

	private final static int LIMIT_TIME = Integer.MAX_VALUE;

	/**
	 * Constructor of OMap
	 */
	public OMap() {
		init();
	}

	/**
	 * Method to add an intersection to the intersections of OMap
	 * 
	 * @param x
	 *            longitude of the intersection
	 * @param y
	 *            latitude of the intersection
	 * @param id
	 *            id of the intersection
	 */
	public void addIntersection(int x, int y, Integer id) {
		intersections.put(id, new Intersection(x, y, id));

		if (x > maxXIntersection) {
			maxXIntersection = x;
		}
		if (y > maxYIntersection) {
			maxYIntersection = y;
		}
		if (x < minXIntersection || minXIntersection == 0) {
			minXIntersection = x;
		}
		if (y < minYIntersection || minYIntersection == 0) {
			minYIntersection = y;
		}
	}

	/**
	 * Method to add a section to the Sections of OMap
	 * 
	 * @param start
	 *            id of the beginning of the section
	 * @param end
	 *            id of the ending of the section
	 * @param length
	 *            length of an intersection (in decimeters)
	 * @param averageSpeed
	 *            speed on the intersection (in decimeters/second)
	 * @param name
	 *            name of the intersection
	 */
	public void addSection(Integer start, Integer end, int length, int averageSpeed, String name) {
		sections.put(new Pair<Integer, Integer>(start, end),
				new Section(name, length, averageSpeed, intersections.get(start), intersections.get(end)));
	}

	/**
	 * Method to create the deliveryOrder, using the warehouse (the first point
	 * of the intersection) and a list of deliveries
	 * 
	 * @param warehouseId
	 *            intersection's id of the warehouse
	 * @param deliveries
	 *            list of deliveries that order should do
	 */
	public void buildeDeliveryOrder(Integer warehouseId, String warehouseDepartureTime, int[][] deliveries) {
		deliveryOrder = new DeliveryOrder(warehouseDepartureTime, intersections.get(warehouseId));

		for (int i = 0; i < deliveries.length; i++) {
			deliveryOrder.addDelivery(new Delivery(intersections.get(deliveries[i][0]), deliveries[i][1]));
		}
	}

	/**
	 * Method to create the deliveryOrder, using the warehouse (the first point
	 * of the intersection), a list of deliveries and the time window
	 * 
	 * @param warehouseId
	 *            Intersection's id of the warehouse
	 * @param warehouseDepartureTime
	 *            Departure time of the warehouse
	 * @param deliveries
	 *            List of deliveries in the delivery order
	 * @param timeWindow
	 *            2 string for each delivery, one for the start of the time
	 *            window, one for the end
	 */
	public void buildeDeliveryOrder(Integer warehouseId, String warehouseDepartureTime, int[][] deliveries,
			String[][] timeWindow) {
		deliveryOrder = new DeliveryOrder(warehouseDepartureTime, intersections.get(warehouseId));

		for (int i = 0; i < deliveries.length; i++) {

			if (timeWindow[i][0].equals("") && timeWindow[i][1].equals("")) { // if
																				// no
																				// time
																				// window
				deliveryOrder.addDelivery(new Delivery(intersections.get(deliveries[i][0]), deliveries[i][1]));

			} else {
				deliveryOrder.addDelivery(new Delivery(intersections.get(deliveries[i][0]), deliveries[i][1],
						timeWindow[i][0], timeWindow[i][1]));
			}
		}
	}

	/**
	 * @brief Compute the delivery round Creates all the possibles Routes
	 *        between each delivery in function of the deliveries of the
	 *        deliveryOrder and sort them by the method of the TSP
	 */
	public void computeRound() {
		int[] deliveriesDurations = deliveryOrder.getDeliveriesDurations();
		int deliveriesNumber = deliveriesDurations.length;
		Hour departureTime = deliveryOrder.getWarehouseDepartureTime();
		Hour[][] deliveriesTimeWindows = deliveryOrder.getDeliveriesTimeWindows();

		int[][] costs = searchPossiblesRoutes();

		tsp.searchSolution(LIMIT_TIME, deliveriesNumber, costs, deliveriesDurations, deliveriesTimeWindows[0],
				deliveriesTimeWindows[1], departureTime);
	}

	/**
	 * @brief Use the Dijkstra algorithm to return all the shortest routes in
	 *        the map to go from one delivery to another
	 * 
	 * @param costs
	 *            : costs[i][j] is cost of the Route[i][j] (sum of sections
	 *            durations of the Route + the delivery duration between each
	 *            section) (/!\ tab modified by the method)
	 * @return the tab of the routes : Route[i][j] is the route to go from the
	 *         delivery i to the delivery j in the deliveryOrder List
	 */

	private int[][] searchPossiblesRoutes() {
		int[][] costs;

		if (deliveryOrder != null) {
			List<Delivery> deliveries = new ArrayList<Delivery>();
			// the warehouse is now considered as the first delivery
			deliveries.add(new Delivery(deliveryOrder.getWarehouseAddress(), 0));
			deliveries.addAll(deliveryOrder.getDeliveries());

			int deliveriesNumber = deliveries.size();
			possibleRoutes = new Route[deliveriesNumber][deliveriesNumber];
			costs = new int[deliveriesNumber][deliveriesNumber];

			for (int i = 0; i < deliveriesNumber; i++) {
				int departure = deliveries.get(i).getAddress().getId();
				Dijkstra dijkstra = new Dijkstra(this, departure);
				dijkstra.compute();

				for (int j = 0; j < deliveriesNumber; j++) {

					if (j != i) {
						int arrival = deliveries.get(j).getAddress().getId();

						costs[i][j] = dijkstra.getDistance(arrival);
						List<Section> routeSections = new LinkedList<Section>();

						// add backwardly all the section in the future Route
						// List to the departure
						while (arrival != departure) {

							int predecessorIndice = dijkstra.getPredecessor(arrival);
							routeSections.add(0, sections.get(new Pair<Integer, Integer>(arrival, predecessorIndice)));
							arrival = predecessorIndice;
						}
						possibleRoutes[i][j] = new Route(deliveries.get(i), deliveries.get(j), routeSections);
					}
				}
			}

		} else {
			throw new NullPointerException("deliveryOrder not defined before dijkstra");
		}

		return costs;
	}

	@Override
	public void release(Integer departure, Integer arrival, Map<Integer, Integer> predecessors,
			Map<Integer, Integer> distances) {
		Section section = sections.get(new Pair<Integer, Integer>(departure, arrival));

		if (section != null) {
			int cost = section.getPassageDuration();

			if (distances.get(departure) != null) { // if distance to departure
													// isn't infinte

				if (distances.get(arrival) == null) { // if distance to arrival
														// is still infinte
					distances.put(arrival, distances.get(departure) + cost);
					predecessors.put(arrival, departure);

				} else if (distances.get(arrival) > distances.get(departure) + cost) {
					distances.put(arrival, distances.get(departure) + cost);
					predecessors.put(arrival, departure);
				}
			}

		} else {
			throw new NullPointerException("section " + departure + " to " + arrival + " doesn't exists");
		}
	}

	@Override
	public List<Integer> getSuccessors(Integer node) {
		List<Integer> successors = new LinkedList<Integer>();
		Set<Integer> ints = intersections.keySet();

		for (Integer intersection : ints) {
			if (sections.containsKey(new Pair<Integer, Integer>(node, intersection))) {
				successors.add(intersection);
			}
		}

		return successors;
	}

	/**
	 * Get the map of intersections
	 * 
	 * @return Map of intersections
	 */
	public Map<Integer, Intersection> getIntersections() {
		return this.intersections;
	}

	/**
	 * Get the map of sections
	 * 
	 * @return Map of sections
	 */
	public Map<Pair<Integer, Integer>, Section> getSections() {
		return this.sections;
	}

	/**
	 * Get the delivery order
	 * 
	 * @return Delivery order
	 */
	public DeliveryOrder getDeliveryOrder() {
		return this.deliveryOrder;
	}

	/**
	 * Get the delivery round
	 * 
	 * @return Delivery round
	 */
	public DeliveryRound getDeliveryRound() {
		return this.deliveryRound;
	}

	/**
	 * notify the observers that the model has changed
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Remove everything from the omap
	 */
	public void clean() {
		init();
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		int deliveriesNumber = deliveryOrder.getDeliveries().size();

		if (tsp.solutionFound()) {
			List<Route> journey = new LinkedList<Route>();

			int departure = 0;
			int arrival = 0;

			for (int i = 0; i < deliveriesNumber; i++) {
				departure = tsp.getBestSolution(i);
				arrival = tsp.getBestSolution(i + 1);
				journey.add(possibleRoutes[departure][arrival]);
			}

			// add the return from the last delivery to the warehouse
			journey.add(possibleRoutes[arrival][tsp.getBestSolution(0)]);

			Hour warehouseDepartureTime = deliveryOrder.getWarehouseDepartureTime();

			deliveryRound = new DeliveryRound(warehouseDepartureTime, journey);
		}

		update();
	}

	/**
	 * @return true if the map has correctly computed delivery round
	 */
	public boolean solutionFound() {

		if (tsp.solutionFound()) {
			return deliveryRound != null;
		}

		return false;
	}

	/**
	 * stops the computing of the delivery Round, if its running
	 */
	public void stopComuting() {
		tsp.stopComputing();
	}

	/**
	 * @return true if the model doesn't search a solution of delivery round
	 */
	public boolean isTspStopped() {
		return tsp.isStopped();
	}

	/**
	 * Get the max X in the intersections
	 * 
	 * @return
	 */
	public float getMaxXIntersection() {
		return maxXIntersection;
	}

	/**
	 * Get the max Y in the intersections
	 * 
	 * @return
	 */
	public float getMaxYIntersection() {
		return maxYIntersection;
	}

	/**
	 * Get the min X in the intersections
	 * 
	 * @return
	 */
	public float getMinXIntersection() {
		return minXIntersection;
	}

	/**
	 * Get the min Y in the intersections
	 * 
	 * @return
	 */
	public float getMinYIntersection() {
		return minYIntersection;
	}

	/**
	 * initiate all the attributes of OMap
	 */
	private void init() {
		deliveryOrder = null;
		deliveryRound = null;
		intersections = new HashMap<Integer, Intersection>();
		sections = new HashMap<Pair<Integer, Integer>, Section>();
		maxXIntersection = 0;
		maxYIntersection = 0;
		minXIntersection = 0;
		minYIntersection = 0;
		tsp = new TSP3Observable();
		tsp.addObserver(this);
		possibleRoutes = null;
		selectedDelivery = null;
		selectedIntersection = null;
	}

	public void setSelectedDelivery(Delivery selectedDelivery) {
		this.selectedDelivery = selectedDelivery;
		update();
	}

	public Delivery getSelectedDelivery() {
		return selectedDelivery;
	}

	public void setSelectedDeliveryIndex(int index) {
		this.selectedDeliveryIndex = index;
	}

	public int getSelectedDeliveryIndex() {
		return this.selectedDeliveryIndex;
	}

	public void setSelectedIntersection(Intersection selectedIntersection) {
		this.selectedIntersection = selectedIntersection;
		update();
	}

	public Intersection getSelectedIntersection() {
		return selectedIntersection;
	}

	/**
	 * Add a delivery to the delivery round
	 * 
	 * @param idIntersection
	 *            Address of the delivery
	 * @param deliveryDuration
	 *            Duration of the delivery
	 * @param index
	 *            Where to place the new delivery in the list of deliveries
	 */
	public void addDeliveryToDeliveryRound(int idIntersection, int deliveryDuration, int index) {
		Delivery newDelivery = new Delivery(intersections.get(idIntersection), deliveryDuration);

		int indexNewDelivery = deliveryOrder.getDeliveries().size() + 1;
		deliveryOrder.addDelivery(newDelivery);

		searchPossiblesRoutes();

		Route previousRoute = deliveryRound.getJourney().get(index - 1);

		Delivery previousDelivery = previousRoute.getDeliveryDeparture();
		Delivery nextDelivery = previousRoute.getDeliveryArrival();

		int previousDeliveryIndex = deliveryOrder.getDeliveries().indexOf(previousDelivery) + 1;
		int nextDeliveryIndex = deliveryOrder.getDeliveries().indexOf(nextDelivery) + 1;

		Route[] newRoutes = new Route[2];
		newRoutes[0] = possibleRoutes[previousDeliveryIndex][indexNewDelivery];
		newRoutes[1] = possibleRoutes[indexNewDelivery][nextDeliveryIndex];

		deliveryRound.addDelivery(newRoutes, index);
	}

	/**
	 * Remove a delivery from the delivery round
	 * 
	 * @param delivery
	 *            Delivery to remove
	 * @param index
	 *            Index where is the delivery is the list of the delivery in the
	 *            delivery round
	 */
	public void deleteDeliveryToDeliveryRound(Delivery delivery, int index) {
		deliveryOrder.removeDelivery(delivery);

		searchPossiblesRoutes();

		Route previousRoute = deliveryRound.getJourney().get(index - 1);
		Route nextRoute = deliveryRound.getJourney().get(index);

		Delivery previousDelivery = previousRoute.getDeliveryDeparture();
		Delivery nextDelivery = nextRoute.getDeliveryArrival();

		int previousDeliveryIndex = deliveryOrder.getDeliveries().indexOf(previousDelivery) + 1;
		int nextDeliveryIndex = deliveryOrder.getDeliveries().indexOf(nextDelivery) + 1;

		Route newRoute = possibleRoutes[previousDeliveryIndex][nextDeliveryIndex];

		deliveryRound.removeDelivery(newRoute, index);
	}

	/**
	 * Get a delivery in the delivery round by its index
	 * 
	 * @param index
	 *            Index of the delivery in the delivery round
	 * @return
	 */
	public Delivery getDeliveryFromDeliveryRoundByIndex(int index) {
		return deliveryRound.getJourney().get(index).getDeliveryDeparture();
	}

	/**
	 * Add a delivery to the delivery round
	 * 
	 * @param newDelivery
	 *            Delivery to add
	 * @param index
	 *            Index where to place the new delivery in the delivery round
	 */
	public void addDeliveryToDeliveryRound(Delivery newDelivery, int index) {
		int indexNewDelivery = deliveryOrder.getDeliveries().size() + 1;
		deliveryOrder.addDelivery(newDelivery);

		searchPossiblesRoutes();

		Route previousRoute = deliveryRound.getJourney().get(index - 1);

		Delivery previousDelivery = previousRoute.getDeliveryDeparture();
		Delivery nextDelivery = previousRoute.getDeliveryArrival();

		int previousDeliveryIndex = deliveryOrder.getDeliveries().indexOf(previousDelivery) + 1;
		int nextDeliveryIndex = deliveryOrder.getDeliveries().indexOf(nextDelivery) + 1;

		Route[] newRoutes = new Route[2];
		newRoutes[0] = possibleRoutes[previousDeliveryIndex][indexNewDelivery];
		newRoutes[1] = possibleRoutes[indexNewDelivery][nextDeliveryIndex];

		deliveryRound.addDelivery(newRoutes, index);
	}
}
