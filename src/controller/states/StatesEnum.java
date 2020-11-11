package controller.states;

import controller.Controller;

/**
 * @brief Helper enum to set the states in the controller
 * 
 * This enumeration lists all the states that can be used in the application.
 * If a state is not listed here, it will not be used.
 * 
 * @author Nathan Shiraini
 *
 */
public enum StatesEnum {

	DEFAULT,
	WAIT_FOR_MAP,
	WAIT_FOR_ORDER,
	FILES_LOADED,
	COMPUTING_ROUND,
	ROUND_COMPUTED;
	
	///@brief Initial state of the application
	public static final StatesEnum STARTUP = WAIT_FOR_MAP;
	
	/**
	 * @brief Creates an instance of a specific state implementation
	 * 
	 * This method creates a new instance of the state implementation that matches the enum value passed as a parameter.
	 * 
	 * @param state A value of this enum that corresponds to the state to create
	 * @param controller The application controller to which the newly created state will be linked
	 * @return The state instance that has been created
	 */
	public static State makeState(StatesEnum state, Controller controller) {
		switch(state){
		case WAIT_FOR_MAP:
			return new WaitForMapState(controller);
		case WAIT_FOR_ORDER:
			return new WaitForOrderState(controller);
		case FILES_LOADED:
			return new FilesLoadedState(controller);
		case ROUND_COMPUTED:
			return new RoundComputedState(controller);
		case COMPUTING_ROUND:
			return new ComputingRoundState(controller);
		case DEFAULT: // falls through
		default:
			return new DefaultState(controller);
		}
	}
	
	///@brief SHortcut to <tt>StatesEnum.makeState(STARTUP, controller)</tt>
	public static State startupState(Controller controller) {
		return makeState(STARTUP, controller);
	}
}
