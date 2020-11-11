package controller.states;

import controller.Controller;


/**
 * @brief The application has just loaded an order file
 * 
 * This state is active when both a street map and a delivery order file have been loaded.
 * The next step is the calculation of the delivery round. 
 * 
 * @author Thibaut FERNANDEZ
 *
 */
public class FilesLoadedState extends WaitForOrderState {

	protected static final String ENTRY_MESSAGE = "Order loaded succesfully. Ready to compute the delivery round.";

	/**
	 * @brief Base constructor for this state class
	 * 
	 * This constructor only prints a message in the message area.
	 * No other Entry action exists for this state.
	 * 
	 * @param controller A reference to the application controller
	 */
	public FilesLoadedState(Controller controller) {
		super(controller);
		this.controller.getWindow().displayMessage(ENTRY_MESSAGE);
	}
	
	/**
	 * @brief Starts calculating a delivery round
	 * 
	 * in a different thread
	 */
	public void computeOrder() {
		this.controller.setState(StatesEnum.COMPUTING_ROUND);
	}
}
