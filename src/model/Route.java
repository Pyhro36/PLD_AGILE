package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a route, a set of sections
 * @author Joachim
 *
 */
public class Route {
	private Delivery deliveryDeparture;
	private Delivery deliveryArrival;
	private List<Section> sections;
	
	/**
	 * Constructor of a route, knowing only the departure and the arrival 
	 * of the route
	 * @param deliveryDeparture
	 * @param deliveryArrival
	 */
	public Route(Delivery deliveryDeparture, Delivery deliveryArrival) {
		this.deliveryDeparture = deliveryDeparture;
		this.deliveryArrival = deliveryArrival;
		sections=new ArrayList<Section>();
	}
	
	/**
	 * Constructor of a route
	 * @param deliveryDeparture	intersection of the beginning of the route
	 * @param deliveryArrival	intersection of the ending of the route
	 * @param sections			list of sections constituing the route
	 */
	public Route(Delivery deliveryDeparture, Delivery deliveryArrival, List<Section> sections) {
		this.deliveryDeparture = deliveryDeparture;
		this.deliveryArrival = deliveryArrival;
		this.sections=sections;
	}
	
	/**
	 * Method to add a new section to a route
	 * @param section	the section that we want to add to the route
	 * @return 			the return of add from the class Sections
	 */
	public boolean addSection(Section section) {
		return sections.add(section);
	}
	
	/**
	 * Method to know the duration of a route
	 * @return an int corresponding to the duration of a route in seconds
	 */
	public int getRouteDuration() {
		int routeDuration = 0;
		
		for(Section section : sections) {
			routeDuration += section.getPassageDuration();
		}
		
		routeDuration += deliveryArrival.getDeliveryDuration();
		
		return routeDuration;
	}
	
	/**
	 * Getter to know the intersection of the beginning of the route
	 * @return the delivery of departure of the route
	 */
	public Delivery getDeliveryDeparture() {
		return deliveryDeparture;
	}
	
	/**
	 * Getter to know the intersection of arrival of the route
	 * @return the delivery of arrival of the route
	 */
	public Delivery getDeliveryArrival() {
		return deliveryArrival;
	}
	
	/**
	 * Getter to get the sections of the route
	 * @return a list of sections constituting the route
	 */
	public List<Section> getSection(){
		return this.sections;
	}
	
	/**
	 * Get the arrival time at the end of the route from the beginning of the route
	 * @param time Time at the beginning of the route
	 * @return Time at the end of the route
	 */
	public Hour getDeliveryArrivalTime(Hour time) {
		int routeDuration = 0;
		
		for(Section section : sections) {
			routeDuration += section.getPassageDuration();
		}
		
		time.addTime(routeDuration);
		
		return time;
	}

}
