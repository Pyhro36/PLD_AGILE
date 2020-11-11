package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a list of deliveries, with the beginning of the delivery (the
 * warehouse).
 * 
 * @author Joachim
 *
 */
public class DeliveryOrder {
	private Hour warehouseDepartureTime;
	private Intersection warehouseAddress;
	private List<Delivery> deliveries;

	/**
	 * Constructor
	 * 
	 * @param warehouseDepartureTime
	 * @param warehouseAddress
	 */
	DeliveryOrder(String warehouseDepartureTime, Intersection warehouseAddress) {
		this.warehouseDepartureTime = new Hour(warehouseDepartureTime);
		this.warehouseAddress = warehouseAddress;
		this.deliveries = new ArrayList<Delivery>();
	}

	/**
	 * Add a new delivery to the order
	 * 
	 * @param delivery
	 * @return
	 */
	boolean addDelivery(Delivery delivery) {
		return deliveries.add(delivery);
	}

	/**
	 * Get the duration of all the deliveries
	 * 
	 * @return
	 */
	int[] getDeliveriesDurations() {
		int[] deliveriesDurations = new int[deliveries.size() + 1];

		deliveriesDurations[0] = 0;

		for (int i = 1; i < deliveries.size() + 1; i++) {
			deliveriesDurations[i] = deliveries.get(i - 1).getDeliveryDuration();
		}

		return deliveriesDurations;
	}

	/**
	 * @return the tab of time window of the deliveries : for the delivery i (begin by warehouse) Hour[0][i] is the start of the time window, and Hour[1][i] is its end
	 * if the delivery has no time window, those Hour[0][i] and Hour[1][i] are null
	 */
	Hour[][] getDeliveriesTimeWindows() {
		Hour[][] deliveriesTimeWindows = new Hour[2][deliveries.size() + 1];
		
		for (int i = 1; i < deliveries.size() + 1; i++) {
			deliveriesTimeWindows[0][i] = deliveries.get(i - 1).getTimeWindowStart();
			deliveriesTimeWindows[1][i] = deliveries.get(i - 1).getTimeWindowEnd();
		}
		
		return deliveriesTimeWindows;
	}

	/**
	 * Get the departure time from the warehouse
	 * 
	 * @return Hour
	 */
	public Hour getWarehouseDepartureTime() {
		return warehouseDepartureTime;
	}

	/**
	 * Get the address of the warehouse
	 * 
	 * @return an intersection
	 */
	public Intersection getWarehouseAddress() {
		return warehouseAddress;
	}

	/**
	 * Get all the deliveries of the order
	 * 
	 * @return
	 */
	public List<Delivery> getDeliveries() {
		return deliveries;
	}
	
	/**
	 * Remove a specific delivery from the list of delivery of the delivery order
	 * @param delivery Delivery to remove
	 */
	public void removeDelivery(Delivery delivery) {
		deliveries.remove(delivery);
	}
}
