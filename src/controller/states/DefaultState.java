package controller.states;

import controller.Controller;

/**
 * @brief Default state implementation
 * 
 * This class is the default implementation of the State interface.
 * The methods implemented in this class do not perform any action.
 * 
 * @warning Setting an instance of this class as an active state will hang the application.
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public class DefaultState implements State {

	protected final Controller controller;
	
	/**
	 * @brief Default constructor
	 * 
	 * This constructor does not perform any action
	 * except storing the reference to the application controller.
	 * 
	 * @param controller A reference to the application controller
	 */
	public DefaultState(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void openMap() {/* does nothing */}

	@Override
	public void addOrder() {/* does nothing */}

	@Override
	public void computeOrder() {/* does nothing */}

	@Override
	public void undo() {/* does nothing */}

	@Override
	public void redo() {/* does nothing */}

	@Override
	public void addDelivery(int intersectionID, int timeBegin, int timeEnd, int duration, int index) {/* does nothing */}

	@Override
	public void deleteDelivery(int deliveryIndex) {/* does nothing */}

	@Override
	public void moveDelivery(int deliveryIndex, boolean up) {/* does nothing */}

	@Override
	public void stopComputing() {/* does nothing */}
	
	@Override
	public void exportOrder() {/* does nothing */}

}
