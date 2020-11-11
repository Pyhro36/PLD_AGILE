package model;

import java.util.List;

import model.Route;

/**
 * Routes to take in order to finish a delivery
 * @author Joachim
 *
 */
public class DeliveryRound {
	private Hour warehouseDepartureTime;
	private Hour warehouseArrivalTime;
	private List<Route> journey;

	/**
	 * Constructor
	 * @param warehouseDepartureTime
	 * @param journey
	 */
	public DeliveryRound(Hour warehouseDepartureTime, List<Route> journey) {
		this.warehouseDepartureTime = warehouseDepartureTime;
		this.journey = journey;
		this.warehouseArrivalTime = new Hour(this.warehouseDepartureTime.getTimeString());
		this.warehouseArrivalTime.addTime(this.getDeliveryRoundDuration());
		setDeliveriesArrivalTime();
	}

	/**
	 * Get the duration of the delivery
	 * @return a int, corresponding to the duration of the delivery in seconds
	 */
	public int getDeliveryRoundDuration() {
		int deliveryRoundDuration = 0;

		// TODO
		for(Route route : journey) {
			deliveryRoundDuration += route.getRouteDuration();
		}

		return deliveryRoundDuration;
	}

	/**
	 * Getting the journey (all the routes) taken in order to do the deliveries
	 * @return a list of the route the driver should take
	 */
	public List<Route> getJourney(){
		return this.journey;
	}
	
	/**
	 * Get the departure time of the warehouse
	 * @return Hour
	 */
	public Hour getDepartureTime() {
		return this.warehouseDepartureTime;
	}
	
	/**
	 * Get the arrival time at the warehouse
	 * @return Hour
	 */
	public Hour getArrivalTime() {
		return this.warehouseArrivalTime;
	}
	
	/**
	 * Get the arrival time at each delivery point
	 * @return 
	 */
	public void setDeliveriesArrivalTime() {
		Hour time = new Hour(this.warehouseDepartureTime.getTimeString());
		
		for(Route route : journey) {
			time = route.getDeliveryArrivalTime(time);
			route.getDeliveryArrival().setArrivalTime(time);
			time = route.getDeliveryArrival().getDeliveryTime();
		}
	}
	
	/**
	 * Refresh the end time of the delivery round and the arrival time of all the deliveries
	 */
	public void refreshArrivalTime() {
		this.warehouseArrivalTime = new Hour(this.warehouseDepartureTime.getTimeString());
		this.warehouseArrivalTime.addTime(this.getDeliveryRoundDuration());
		setDeliveriesArrivalTime();
	}
	
	/**
	 * Add a delivery to the delivery round
	 * @param routes Routes to go from the previous delivery to the new, and from the new to the next
	 * @param index Indicate where the delivery has to be added in the list
	 */
	public void addDelivery(Route[] routes, int index) {
		journey.set(index-1, routes[0]);
		journey.add(index, routes[1]);	
		refreshArrivalTime();
	}
	
	/**
	 * Remove a delivery from the delivery round
	 * @param route Route to go from the previous delivery to the next
	 * @param index Index of the delivery to remove
	 */
	public void removeDelivery(Route route, int index) {
		journey.set(index - 1, route);
		journey.remove(index);
		refreshArrivalTime();
	}
	

}
