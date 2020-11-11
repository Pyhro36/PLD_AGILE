package model;

/**
 * Class for a delivery
 * @author Joachim
 *
 */
public class Delivery {
	private int deliveryDuration;
	private Intersection address;
	private Hour timeWindowStart;
	private Hour timeWindowEnd;
	private Hour arrivalTime;
	private Hour deliveryTime;
	
	/**
	 * Constructor
	 * @param address Intersection of the delivery
	 * @param deliveryDuration duration of the delivery
	 */
	public Delivery(Intersection address, int deliveryDuration) {
		this.address = address;
		this.deliveryDuration = deliveryDuration;
		this.timeWindowStart = null;
		this.timeWindowEnd = null;
	}
	
	/**
	 * Constructor with time window
	 * @param address Intersection of the delivery
	 * @param deliveryDuration Duration of the delivery
	 * @param timeWindowStart Beginning of the time window
	 * @param timeWindowEnd End of the time window
	 */
	public Delivery(Intersection address, int deliveryDuration, String timeWindowStart, String timeWindowEnd) {
		this.address = address;
		this.deliveryDuration = deliveryDuration;
		this.timeWindowStart = new Hour(timeWindowStart);
		this.timeWindowEnd = new Hour(timeWindowEnd);
	}
	
	/**
	 * Get the duration of a delivery
	 * @return int
	 */
	public int getDeliveryDuration() {
		return deliveryDuration;
	}
	
	/**
	 * Get the intersection of an address
	 * @return
	 */
	public Intersection getAddress() {
		return address;
	}
	
	/**
	 * 
	 * @param vd
	 */
	public void displayDelivery(VisitorDelivery vd){
		vd.displayDelivery(this);
	}
	
	/**
	 * Set the arrival time of the delivery and calculate the time when the delivery is done
	 * @param arrivalTime Arrival time at the delivery point
	 * @return The time when the delivery is done
	 */
	public Hour setArrivalTime(Hour arrivalTime) {
		this.arrivalTime = arrivalTime;
		
		deliveryTime = new Hour(this.arrivalTime.getTimeString());
		deliveryTime.addTime(deliveryDuration);
		
		return deliveryTime;
	}
	
	/**
	 * Get the arrival time at the delivery point
	 * @return Arrival time
	 */
	public Hour getArrivalTime() {
		return this.arrivalTime;
	}
	
	/**
	 * Get the beginning of the time window
	 * @return Beginning of the time window
	 */
	public Hour getTimeWindowStart() {
		return timeWindowStart;
	}

	/**
	 * Get the end of the time window
	 * @return End of the time window
	 */
	public Hour getTimeWindowEnd() {
		return timeWindowEnd;
	}

	/**
	 * Get the time when the delivery is done
	 * @return Time when the delivery is done
	 */
	public Hour getDeliveryTime() {
		return this.deliveryTime;
	}
}
