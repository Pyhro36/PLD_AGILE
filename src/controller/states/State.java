package controller.states;

/**
 * @brief Application states base interface
 * 
 * This interface lists all the actions that can be performed by the application states.
 * These actions will be implemented in state classes that will be called by the controller.
 * 
 * @authors Thibaut FERNANDEZ
 * @authors Joachim DOREL
 *
 */
public interface State {

	/**
	 * @brief Method called by the controller after a click on the OpenMap button
	 */
	public void openMap();
	
	/**
	 * @brief Method called by the controller after a click on the addOrder button
	 */
	public void addOrder();
	
	/**
	 * @brief Method to compute the delivery Round
	 */
	public void computeOrder();
	
	/**
	 * @brief Undo a command
	 */
	public void undo();
	
	/**
	 * @brief Redo a command
	 */
	public void redo();
	
	/**
	 * @brief Add a delivery to the list
	 * 
	 * @param intersectionID The ID number of the intersection to which the delivery will be made
	 * @param timeBegin The beginning timestamp for the delivery time frame
	 * @param timeEnd The ending timestamp for the delivery time frame
	 * @param duration The duration of the delivery stop
	 * @param index The position in the list at which the delivery will be inserted
	 */
	public void addDelivery(int intersectionID, int timeBegin, int timeEnd, int duration, int index);
	
	/**
	 * @brief Remove a delivery from the list
	 * 
	 * @param deliveryIndex The index in the list of the delivery to remove
	 */
	public void deleteDelivery(int deliveryIndex);
	
	/**
	 * @brief Move a delivery in the list
	 * 
	 * Moving a delivery "up" means swapping it with the delivery that would be processed before.
	 * Its index in the underlying delivery list will be decreased.
	 * 
	 * Moving the delivery "down" will increase its index.
	 * 
	 * @param deliveryIndex The index in the list of the delivery to move
	 * @param up @c true if the delivery is moved up, @c false if it is moved down
	 */
	public void moveDelivery(int deliveryIndex, boolean up);
	
	/**
	 * @Brief method to implement in order to stop the computing of the Round
	 */
	public void stopComputing();
	
	public void exportOrder();
}
